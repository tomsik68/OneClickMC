package sk.tomsik68.oneclickmc;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import sk.tomsik68.mclauncher.api.common.ILaunchSettings;
import sk.tomsik68.mclauncher.api.common.IObservable;
import sk.tomsik68.mclauncher.api.common.IObserver;
import sk.tomsik68.mclauncher.api.common.MCLauncherAPI;
import sk.tomsik68.mclauncher.api.common.mc.MinecraftInstance;
import sk.tomsik68.mclauncher.api.login.IProfile;
import sk.tomsik68.mclauncher.api.login.IProfileIO;
import sk.tomsik68.mclauncher.api.login.ISession;
import sk.tomsik68.mclauncher.api.versions.IVersion;
import sk.tomsik68.mclauncher.api.versions.LatestVersionInformation;
import sk.tomsik68.mclauncher.backend.GlobalAuthenticationSystem;
import sk.tomsik68.mclauncher.backend.MinecraftLauncherBackend;
import sk.tomsik68.mclauncher.impl.common.Platform;
import sk.tomsik68.mclauncher.impl.login.legacy.LegacyProfile;
import sk.tomsik68.mclauncher.impl.login.yggdrasil.YDLoginService;
import sk.tomsik68.mclauncher.impl.login.yggdrasil.YDProfileIO;
import sk.tomsik68.mclauncher.impl.versions.mcdownload.MCDownloadVersionList;
import sk.tomsik68.mclauncher.util.HttpUtils;

public class GameLauncher {
    private ProgressDialog progressDialog;
    private MinecraftLauncherBackend minecraftLauncherBackend;

    public GameLauncher() {
        minecraftLauncherBackend = new MinecraftLauncherBackend(Platform.getCurrentPlatform().getWorkingDirectory());
    }

    public void play(int profile) throws Exception {
        progressDialog = new ProgressDialog();
        progressDialog.setMessage("Hello World!");
        progressDialog.setVisible(true);

        progressDialog.setMessage("Logging in...");

        MinecraftInstance mc = new MinecraftInstance(Platform.getCurrentPlatform().getWorkingDirectory());

        ISession session = GlobalAuthenticationSystem.login(null);
        progressDialog.setMessage("Checking for updates...");
        LatestVersionInformation latest = minecraftLauncherBackend.getLatestVersionInformation();
        String lastVersion = latest.getLatestRelease();
        minecraftLauncherBackend.updateMinecraft(lastVersion, progressDialog);

        progressDialog.setMessage("Launching...");
        progressDialog.setVisible(false);

        ProcessBuilder pb = minecraftLauncherBackend.launchMinecraft(session, lastVersion);
        pb.redirectErrorStream(true);
        Process proc = pb.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        while (isProcessAlive(proc)) {
            String line = br.readLine();
            if (line != null && line.length() > 0)
                System.out.println(line);
        }

        OneClickMCMainFrame.close();
    }

    private boolean isProcessAlive(Process proc) {
        try {
            proc.exitValue();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

}
