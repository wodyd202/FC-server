package com.fc.core.event;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import com.fc.core.domain.AggregateRoot;
import com.fc.core.snapshot.Snapshot;
import com.fc.core.snapshot.SnapshotRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings({"rawtypes","unchecked"})
public abstract class AbstractEventHandler<A extends AggregateRoot, ID> implements EventHandler<A, ID> {

	private final Class<A> aggregateType;

	private EventStore<ID> eventStore;

	private SnapshotRepository<A, ID> snapshotRepository;

	private static final int SNAPSHOT_COUNT = 10;

	public AbstractEventHandler(EventStore eventStore, SnapshotRepository snapshotRepository) {
		this.eventStore = eventStore;
		this.snapshotRepository = snapshotRepository;
		this.aggregateType = aggregateType();
	}

	private Class<A> aggregateType() {
		Type genType = this.getClass().getGenericSuperclass();
		if (genType instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

			if ((params != null) && (params.length >= 1)) {
				return (Class) params[0];
			}
		}
		return null;
	}

	private A createAggregateRootViaReflection(ID identifier) {
		try {
			Constructor[] constructors = aggregateType.getDeclaredConstructors();
			for (Constructor constructor : constructors) {
				if (constructor.getParameterCount() == 1) {
					constructor.setAccessible(true);
					return (A) constructor.newInstance(identifier);
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		throw new IllegalArgumentException("Aggregate에 identifier를 argument로 받는 생성자가 없음");
	}

	@Override
	public void save(A aggregateRoot) {
		final ID identifier = (ID) aggregateRoot.getIdentifier();
		eventStore.saveEvents(identifier, aggregateRoot.getExpectedVersion(), aggregateRoot.getUncommittedChanges());

		aggregateRoot.markChangesAsCommitted();
		
		long countEvents = eventStore.countEvents(identifier);

		if ((countEvents % SNAPSHOT_COUNT) == 0) {
			log.debug("{} snapshot count {}", identifier, countEvents);
			Snapshot<A, ID> snapshot = new Snapshot<>(identifier, aggregateRoot.getExpectedVersion(), aggregateRoot);
			snapshotRepository.save(snapshot);
			return;
		}
	}

	@Override
	public Optional<A> find(ID identifier) {
		// snapshot저장소에서 호출함
		A aggregateRoot = createAggregateRootViaReflection(identifier);

		Optional<Snapshot<A, ID>> retrieveSnapshot = retrieveSnapshot(identifier);
		List<Event<ID>> baseEvents;

		if (retrieveSnapshot.isPresent()) {
			Snapshot<A, ID> snapshot = retrieveSnapshot.get();
			baseEvents = eventStore.getEventsByAfterVersion(snapshot.getIdentifier(), snapshot.getVersion());
			// snapshot에 저장된 aggregateRoot객체를 로딩함.
			aggregateRoot = snapshot.getAggregateRoot();
		} else {
			baseEvents = eventStore.getEvents(identifier);
		}
		
		if (baseEvents == null || baseEvents.size() == 0) {
			return Optional.ofNullable(null);
		}
		
		aggregateRoot.replay(baseEvents);

		return Optional.ofNullable(aggregateRoot);
	}

	private Optional<Snapshot<A, ID>> retrieveSnapshot(ID identifier) {
		if (snapshotRepository == null) {
			return Optional.empty();
		}
		return snapshotRepository.findLatest(identifier);
	}

}
