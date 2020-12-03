package diplom.service;

import diplom.model.GlobalSetting;
import diplom.repository.GlobalSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlobalSettingService {
    private final GlobalSettingRepository globalSettingRepository;

    //------------------------------------------------------------------------------------------------------------------

    public List<GlobalSetting> findAll() {
        return globalSettingRepository.findAll();
    }
}
