//package bg.solutions.hitachi.space.commands;
//
//import bg.solutions.hitachi.space.mission.SpaceMissionAPI;
//
//import java.nio.channels.SocketChannel;
//
//public class CommandExecutor {
//    private final SpaceMissionAPI missionAPI;
//
//    public CommandExecutor(SpaceMissionAPI missionAPI) {
//        this.missionAPI = missionAPI;
//    }
//
//    public String execute(String command) {
//        CommandType commandType = CommandType.valueOf(command.toUpperCase());
//
//        return switch (commandType) {
//            case START -> new RegisterCommand(cmd.arguments(), bookmarkManager).execute();
//            case STOP -> new LoginCommand(cmd.arguments(), clientChannel, bookmarkManager).execute();
//            case STOPPEN -> new LogoutCommand(cmd.arguments(), clientChannel, bookmarkManager).execute();
//            case EN -> new CreateNewGroupCommand(cmd.arguments(), clientChannel, bookmarkManager).execute();
//            case GE -> new AddBookmarkCommand(cmd.arguments(), clientChannel, bookmarkManager).execute();
//            case SEND_EMAIL, E_MAIL_SENDEN -> new RemoveBookmarkCommand(cmd.arguments(), clientChannel, bookmarkManager).execute();
//            case EXPORT_CSV, CSV_EXPORTIEREN -> new SearchBookmarksCommand(cmd.arguments(), clientChannel, bookmarkManager).execute();
//            case FIND_PERFECT_DAY, PERFEKTEN_TAG -> new CleanupCommand(cmd.arguments(), clientChannel, bookmarkManager).execute();
//            default -> "Unknown command";
//        };
//    }
//}
