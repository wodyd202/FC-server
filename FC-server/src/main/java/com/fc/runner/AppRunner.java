package com.fc.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fc.command.store.infra.StoreTagRepository;
import com.fc.domain.store.StoreTag;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AppRunner implements ApplicationRunner {
	private StoreTagRepository storeTagRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		for (int i = 0; i < 50; i++) {
			storeTagRepository.save(new StoreTag("태그_" + i));
		}
	}

}
