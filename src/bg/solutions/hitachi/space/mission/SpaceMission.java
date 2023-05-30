package bg.solutions.hitachi.space.mission;

import bg.solutions.hitachi.space.model.DayWeatherForecast;

import java.util.HashSet;
import java.util.Set;

public class SpaceMission extends SpaceMissionValidator implements SpaceMissionAPI {
    private final String filePath;
    private final String senderEmailAddress;
    private final String password;
    private final String receiverEmailAddress;
    private final Set<DayWeatherForecast> days;

    public SpaceMission(String filePath, String senderEmailAddress, String password, String receiverEmailAddress) {
        validateFilePath(filePath);
        validateSenderEmailAddress(senderEmailAddress);
        validatePassword(password);
        validateReceiverEmailAddress(receiverEmailAddress);

        this.filePath = filePath;
        this.senderEmailAddress = senderEmailAddress;
        this.password = password;
        this.receiverEmailAddress = receiverEmailAddress;
        this.days = new HashSet<>();


    }



}
