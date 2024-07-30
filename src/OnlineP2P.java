import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;


public class OnlineP2P {

    Long playerId, matchId, size;
    Boolean isHost, matchFound;
    String matchState, opponent;
    HttpURLConnection connection = null;
    BufferedReader reader = null;
    DataOutputStream outputStream = null;

    final String addNewPlayerURL = "http://localhost:8090/players/add";
    final String editPlayerUsernameURL = "http://localhost:8090/edit";
    final String hostNewMatchURL = "http://localhost:8090/match/start";
    final String findMatchURL = "http://localhost:8090/match/find";
    final String joinMatchURL = "http://localhost:8090/match/join";
    final String getOpponentUsernameURL = "http://localhost:8090/match/getOpponentUsername";
    final String isOppLastMoveUpdatedURL = "http://localhost:8090/match/isOppLastMoveUpdated";
    final String getOpponentLastMoveURL = "http://localhost:8090/match/getOppLastMove";
    final String updateLastMoveURL = "http://localhost:8090/match/updateLastMove";
    final String leaveMatchURL = "http://localhost:8090/match/leave";
    final String deleteMatchURL = "http://localhost:8090/match/delete";

    public OnlineP2P() {
        
    }

    public int setUpMatch(String username, boolean isHost, Long size) {
        this.size = size;
        playerId = getPlayerId(username);
        // System.out.println(playerId);
        if (isHost) matchId = hostMatch();
        else {
            matchId = findAvailableMatch();
            if (matchFound) joinAsGuest();
            else return -1;
        }
        opponent = getOpponentUsername();
        return 0;
    }

    private void setupConnection(String urlString, String method, String requestBody) throws Exception {
        @SuppressWarnings("deprecation")
        URL url = new URL(urlString);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(requestBody);
        outputStream.flush();
    }

    private StringBuilder getResponse(int successCode, int[] failedCodes, String[] failedMessages) throws Exception {
        StringBuilder response = new StringBuilder();
        if (connection.getResponseCode() == successCode) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine;
            while ((responseLine = reader.readLine()) != null) {
                response.append(responseLine);
            }
        } else {
            int len = failedCodes.length;
            for (int i=0; i<len; i++) {
                if (connection.getResponseCode() == failedCodes[i]) {
                    System.err.println(failedMessages[len] + " : " + failedMessages[i] + " : " + connection.getResponseCode());
                    return null;
                }
            }
            System.err.println(failedMessages[len] + " : " + connection.getResponseCode());
            return null;
        }
        return response;
    }

    private Long getPlayerId(String username) {
        Long id = null;
        String requestBody = "{\"username\":\"" + username + "\"}";
        try {
            setupConnection(addNewPlayerURL, "POST", requestBody);            
            StringBuilder response = getResponse(200, new int[]{}, new String[]{"getPlayerId"});
            if (response!=null) id = Long.parseLong(response.toString().trim());
            else throw new Exception();
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

    private Long hostMatch() {
        Long id = null;
        String requestBody = "{\"hostId\":\"" + playerId + "\", \"size\":\"" + size + "\"}";
        try {
            setupConnection(hostNewMatchURL, "POST", requestBody);
            StringBuilder response = getResponse(200, new int[]{404, 501}, new String[]{"invalid playerID", "invalid size", "hostNewMatch"});
            if (response!=null) {
                id = Long.parseLong(response.toString().trim()); 
                matchFound = true;
            }
            else throw new Exception();
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


    private Long findAvailableMatch() {
        Long id = null;
        String requestBody = "";
        try {
            setupConnection(findMatchURL, "GET", requestBody);
            StringBuilder response = getResponse(302, new int[]{}, new String[]{"findAvailableMatch"});
            if (response!=null) {
                id = Long.parseLong(response.toString().trim()); 
                matchFound = true;
            }
            else {
                matchFound = false;
                throw new Exception();
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



    private void joinAsGuest() {
        String requestBody = "{\"matchId\":\"" + matchId + "\", \"playerId\":\"" + playerId + "\", \"size\":\"" + size + "\"}";
        try {
            setupConnection(joinMatchURL, "PUT", requestBody);
            getResponse(200, new int[]{404}, new String[]{"Not found", "joinAsGuest"});
            
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
    }

    private String getOpponentUsername() {
        String name = null;
        String requestBody = "{\"matchId\":\"" + matchId + "\", \"playerId\":\"" + playerId + "\", \"isHost\":\"" + isHost + "\"}";
        try {
            setupConnection(getOpponentUsernameURL, "GET", requestBody);
            StringBuilder response = getResponse(302, new int[]{}, new String[]{"findAvailableMatch"});
            if (response != null) name = response.toString();
            else throw new Exception();
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
        return name;
    }

    private String getMatchState() {
        return "";
    }

    private void finishRoutine() {

    }

    public void updateMove(int[] moveProps) {
        String requestBody = "{\"matchId\":\"" + matchId + "\", \"playerId\":\"" + playerId + "\", \"isHost\":\"" + isHost + "\", \"currInd:\"" + moveProps[0] + "\", \"currType:\"" + moveProps[1] + "\"}";
        try {
            setupConnection(updateLastMoveURL, "PUT", requestBody);
            StringBuilder response = getResponse(202, new int[]{404}, new String[]{"Not found", "updateMove"});
            if (response == null) throw new Exception();

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
    }

    public boolean isOpponentLastMoveUpdated() {
        boolean isUpdated = false;
        String requestBody = "{\"matchId\":\"" + matchId + "\", \"playerId\":\"" + playerId + "\", \"isHost\":\"" + isHost + "\"}";
        try {
            setupConnection(isOppLastMoveUpdatedURL, "GET", requestBody);
            StringBuilder response = getResponse(200, new int[]{404}, new String[]{"Not found", "isOpponentLastMoveUpdated"});
            if (response != null) isUpdated = Boolean.getBoolean(response.toString());
            else throw new Exception();

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
        return isUpdated;
    }

    public int[] getOpponentMove() {
        int[] moveProps = new int[2];
        return moveProps;
    }
    
}
