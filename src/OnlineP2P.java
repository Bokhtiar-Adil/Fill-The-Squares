import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OnlineP2P {

    Long playerId, matchId;
    Boolean isHost;
    String matchState;
    HttpURLConnection connection = null;
    BufferedReader reader = null;
    DataOutputStream outputStream = null;

    String addNewPlayerURL = "http://localhost:8090/players/add";
    String editPlayerUsernameURL = "http://localhost:8090/edit";
    String hostNewMatchURL = "http://localhost:8090/match/start";
    String findMatchURL = "http://localhost:8090/match/find";
    String joinMatchURL = "http://localhost:8090/match/join";
    String getOpponentLastMoveURL = "http://localhost:8090/match/getOppLastMove";
    String updateLastMoveURL = "http://localhost:8090/match/updateLastMove";
    String leaveMatchURL = "http://localhost:8090/match/leave";
    String deleteMatchURL = "http://localhost:8090/match/delete";

    public OnlineP2P() {
        playerId = getPlayerId();
    }

    public void setUpMatch(boolean isHost) {
        playerId = getPlayerId();
        System.out.println(playerId);
    }

    private Long getPlayerId() {
        Long id = null;
        String requestBody = "{\"username\":\"omar\"}";
        try {
            @SuppressWarnings("deprecation")
            URL url = new URL(addNewPlayerURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(requestBody);
            outputStream.flush();

            if (connection.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String responseLine;
                StringBuilder response = new StringBuilder();

                while ((responseLine = reader.readLine()) != null) {
                    response.append(responseLine);
                }

                id = Long.parseLong(response.toString().trim());
            } else {
                System.err.println("POST request failed: HTTP error code " + connection.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (connection != null) connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return id;
    }

    public Long hostMatch() {
        isHost = true;
        return 0L;
    }

    public Long joinAsGuest() {
        return 0L;
    }

    public String getMatchState() {
        return "";
    }

    public void finishRoutine() {

    }

    public void updateMove(int[] moveProps) {

    }
    public int[] getOpponentMove() {
        int[] moveProps = new int[2];
        return moveProps;
    }
    
}
