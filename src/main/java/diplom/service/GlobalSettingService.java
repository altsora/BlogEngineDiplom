package diplom.service;

import diplom.model.GlobalSetting;
import diplom.repository.GlobalSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GlobalSettingService {
    private final GlobalSettingRepository globalSettingRepository;

    //------------------------------------------------------------------------------------------------------------------

    public Map<String, Boolean> getSettings() {
        return globalSettingRepository.findAll().stream()
                .collect(Collectors.toMap(GlobalSetting::getCodeName, GlobalSetting::enabled));
    }

}
