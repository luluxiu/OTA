package com.ota.ota.repository;


import com.ota.ota.model.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//@Repository
//@Transactional
public interface SettingRepository extends JpaRepository<Settings, Long> {
    Settings findByKey(String key);
}
