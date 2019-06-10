package app.logic.devices.regular.shell;

import app.logic.Signal;
import app.logic.devices.Device;

import java.io.*;
import java.util.concurrent.Executors;

public abstract class ShellCmdDevice implements Device {
    private Signal sendCmd;
    private String command;
    private Signal responseReady;
    private String response;

    public ShellCmdDevice(Signal sendCmd, Signal responseReady) {
        this.sendCmd = sendCmd;
        this.responseReady = responseReady;
        this.command = "";
    }

    @Override
    public void dawn() {
    }

    @Override
    public void dusk() {
        if(sendCmd.isHigh()){
            sendCommand();
        }
    }

    String getCommand() {
        return command;
    }

    void setCommand(String command) {
        this.command = command;
    }

    void sendCommand(){
        try {
            ProcessBuilder builder = new ProcessBuilder();
            //if (isWindows) {
//                builder.command("cmd.exe", "/c", "dir");
            builder.command("cmd.exe", "/c", command);
//            } else {
//                builder.command("sh", "-c", "ls");
//            }
            System.out.println(command);
            builder.directory(new File(System.getProperty("user.dir")));
            Process process = builder.start();
            Executors.newSingleThreadExecutor().submit(() -> {
                StringBuilder stringBuilder = new StringBuilder();
                new BufferedReader(new InputStreamReader(process.getInputStream())).lines()
                        .forEach(stringBuilder::append);
                response = stringBuilder.toString();
                responseIsReady();
            });
            int exitCode = process.waitFor();
            assert exitCode == 0;
        }catch(IOException | InterruptedException ioe){
            ioe.printStackTrace();
        }
    }

    void responseIsReady(){
        responseReady.setState(true);
    }

    String getResponse() {
        return response;
    }

    public String purchaseResponse() {
        responseReady.setState(false);
        return response;
    }
}
