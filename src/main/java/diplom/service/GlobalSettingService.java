package diplom.service;

import diplom.model.GlobalSetting;
import diplom.model.enums.SettingCode;
import diplom.model.enums.SettingValue;
import diplom.repository.GlobalSettingRepository;
import diplom.request.SettingsForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GlobalSettingService {
    private final GlobalSettingRepository globalSettingRepository;

    //------------------------------------------------------------------------------------------------------------------

    public ResponseEntity<String> setSettings(SettingsForm settingsForm) {
        boolean multiUserModeValue = settingsForm.isMultiUserModeValue();
        boolean postPreModerationValue = settingsForm.isPostPreModerationValue();
        boolean statisticsIsPublicValue = settingsForm.isStatisticsIsPublicValue();

        setValue(SettingCode.MULTIUSER_MODE, multiUserModeValue);
        setValue(SettingCode.POST_PREMODERATION, postPreModerationValue);
        setValue(SettingCode.STATISTICS_IS_PUBLIC, statisticsIsPublicValue);

        return ResponseEntity.ok("Настройки успешно сохранены");
    }

    public Map<String, Boolean> getSettings() {
        return globalSettingRepository.findAll().stream()
                .collect(Collectors.toMap(GlobalSetting::getCodeName, GlobalSetting::enabled));
    }

    public boolean multiuserModeEnabled() {
        return settingEnabled(SettingCode.MULTIUSER_MODE);
    }

    public boolean publicStatisticsEnabled() {
        return settingEnabled(SettingCode.STATISTICS_IS_PUBLIC);
    }

    public boolean preModerationEnabled() {
        return settingEnabled(SettingCode.POST_PREMODERATION);
    }

    //------------------------------------------------------------------------------------------------------------------

    private void setValue(SettingCode code, boolean value) {
        SettingValue newValue = value ? SettingValue.YES : SettingValue.NO;
        GlobalSetting setting = globalSettingRepository.findByCode(code);
        setting.setValue(newValue);
        globalSettingRepository.saveAndFlush(setting);
    }

    private boolean settingEnabled(SettingCode code) {
        return globalSettingRepository.existsByCodeAndValue(code, SettingValue.YES);
    }


}
