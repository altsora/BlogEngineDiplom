package diplom.model;

import diplom.enums.SettingCode;
import diplom.enums.SettingValue;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "global_settings")
@Data
public class GlobalSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private SettingCode code;
    private String name;
    @Enumerated(EnumType.STRING)
    private SettingValue value;

    public boolean enabled() {
        return value == SettingValue.YES;
    }

    public String getCodeName() {
        return code.name();
    }
}
