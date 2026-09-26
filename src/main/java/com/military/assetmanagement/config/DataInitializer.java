package com.military.assetmanagement.config;

import com.military.assetmanagement.entity.*;
import com.military.assetmanagement.entity.Role;
import com.military.assetmanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seed(BaseRepository bases, UserRepository users,
                            EquipmentRepository equipment, PasswordEncoder encoder) {
        return args -> {
            Base baseA = bases.findAll().stream().findFirst().orElseGet(() ->
                    bases.save(new Base(null, "Base Alpha", "Location A")));

            Base baseB = bases.findAll().stream().skip(1).findFirst().orElseGet(() ->
                    bases.save(new Base(null, "Base Bravo", "Location B")));

            if (equipment.count() == 0) {
                equipment.save(new Equipment(null, "Utility Vehicle", EquipmentType.VEHICLE,
                        "General purpose vehicle", "unit"));
                equipment.save(new Equipment(null, "Service Weapon", EquipmentType.WEAPON,
                        "Tracked weapon asset", "unit"));
                equipment.save(new Equipment(null, "Ammunition", EquipmentType.AMMUNITION,
                        "Ammunition inventory", "round"));
            }

            if (users.findByUsername("admin").isEmpty()) {
                users.save(new User(null, "admin", encoder.encode("Admin@123"),
                        Role.ADMIN, null, true));
            }
            if (users.findByUsername("commander").isEmpty()) {
                users.save(new User(null, "commander", encoder.encode("Commander@123"),
                        Role.BASE_COMMANDER, baseA, true));
            }
            if (users.findByUsername("logistics").isEmpty()) {
                users.save(new User(null, "logistics", encoder.encode("Logistics@123"),
                        Role.LOGISTICS_OFFICER, baseA, true));
            }
        };
    }
}
