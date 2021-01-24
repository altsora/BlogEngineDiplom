package diplom.repository;

import diplom.model.GlobalSetting;
import diplom.model.enums.SettingCode;
import diplom.model.enums.SettingValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSettingRepository extends JpaRepository<GlobalSetting, Long> {
    boolean existsByCodeAndValue(SettingCode code, SettingValue value);

    GlobalSetting findByCode(SettingCode code);
}
