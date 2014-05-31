package sk.tomsik68.oneclickmc;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import sk.tomsik68.mclauncher.api.common.IObservable;
import sk.tomsik68.mclauncher.api.common.IObserver;
import sk.tomsik68.mclauncher.api.common.MCLauncherAPI;
import sk.tomsik68.mclauncher.api.login.IProfile;
import sk.tomsik68.mclauncher.api.login.IProfileIO;
import sk.tomsik68.mclauncher.api.login.ISession;
import sk.tomsik68.mclauncher.api.versions.IVersion;
import sk.tomsik68.mclauncher.impl.common.Platform;
import sk.tomsik68.mclauncher.impl.common.mc.MinecraftInstance;
import sk.tomsik68.mclauncher.impl.login.legacy.LegacyProfile;
import sk.tomsik68.mclauncher.impl.login.yggdrasil.YDLoginService;
import sk.tomsik68.mclauncher.impl.login.yggdrasil.io.YDProfileIO;
import sk.tomsik68.mclauncher.util.HttpUtils;

public class GameLauncher implements IObserver<IVersion> {
    private String lastRelease;
    private IVersion lastVersion;
    private final IProfileIO profileIO;
    private VersionDownloadThread versionDownloadThread;
    private ProgressDialog progressDialog;

    public GameLauncher() {
        profileIO = new YDProfileIO(Platform.getCurrentPlatform().getWorkingDirectory());
    }

    @Override
    public void onUpdate(IObservable<IVersion> arg0, IVersion arg1) {
        if (arg1.getUniqueID().equalsIgnoreCase("r".concat(lastRelease))) {
            lastVersion = arg1;
        }
    }

    public void downloadVersions() {
        versionDownloadThread = new VersionDownloadThread();
        versionDownloadThread.addObserver(this);

        try {
            JSONObject versions = (JSONObject) JSONValue.parse(HttpUtils.httpGet(MCLauncherAPI.URLS.JSONVERSION_LIST_URL));
            JSONObject latestObject = (JSONObject) versions.get("latest");
            lastRelease = latestObject.get("release").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        versionDownloadThread.start();
    }

    private ISession doLogin(int profileNum) throws Exception {
        File mcInstance = Platform.getCurrentPlatform().getWorkingDirectory();
        ISession result = null;
        IProfile profile = null;
        YDLoginService yls = new YDLoginService();
        IProfile[] profiles;
        try {
            yls.load(mcInstance);
            profiles = profileIO.read();
            profile = profiles[profileNum];
            result = yls.login(profile);

            profile.update(result);
            profileIO.write(profiles);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Couldn't find your profile! Please use the following text fields to login.",
                    "Couldn't find your profile!", JOptionPane.ERROR_MESSAGE);
            profile = new LegacyProfile(JOptionPane.showInputDialog("Please enter your username:"),
                    JOptionPane.showInputDialog("Please enter your password"));
        }

        return result;
    }

    public void play(int profile) throws Exception {
        progressDialog = new ProgressDialog();
        progressDialog.setMessage("Hello World!");
        progressDialog.setVisible(true);
        // wait for the thread to download neccessary information
        progressDialog.setMessage("Downloading version information...");
        while (versionDownloadThread.isAlive() && lastVersion == null) {
        }

        progressDialog.setMessage("Logging in...");

        MinecraftInstance mc = new MinecraftInstance(Platform.getCurrentPlatform().getWorkingDirectory());

        ISession session = doLogin(profile);
        progressDialog.setMessage("Checking for updates...");
        progressDialog.setMessage("Installing MineCraft " + lastVersion.getDisplayName());
        lastVersion.getInstaller().install(lastVersion, mc, null);
        progressDialog.setMessage("Launching...");
        Process proc = lastVersion.getLauncher().launch(session, mc, null, lastVersion, new DefaultLaunchSettings());

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
