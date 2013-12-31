package sk.tomsik68.oneclickmc;

import java.io.File;
import java.util.List;
import java.util.Map;

import sk.tomsik68.mclauncher.api.common.ILaunchSettings;

public class DefaultLaunchSettings implements ILaunchSettings {

    public DefaultLaunchSettings() {
    }


    @Override
    public List<String> getCommandPrefix() {
        return null;
    }

    @Override
    public Map<String, String> getCustomParameters() {
        return null;
    }

    @Override
    public String getHeap() {
        return "1G";
    }

    @Override
    public String getInitHeap() {
        return "256M";
    }

    @Override
    public List<String> getJavaArguments() {
        return null;
    }

    @Override
    public File getJavaLocation() {
        return null;
    }

    @Override
    public boolean isErrorStreamRedirected() {
        return true;
    }

    @Override
    public boolean isModifyAppletOptions() {
        return false;
    }

}
