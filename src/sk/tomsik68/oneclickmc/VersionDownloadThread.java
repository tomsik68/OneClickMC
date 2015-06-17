package sk.tomsik68.oneclickmc;

import sk.tomsik68.mclauncher.api.common.IObservable;
import sk.tomsik68.mclauncher.api.common.IObserver;
import sk.tomsik68.mclauncher.api.versions.IVersion;
import sk.tomsik68.mclauncher.impl.versions.mcdownload.MCDownloadVersionList;

public class VersionDownloadThread extends Thread implements IObservable<String> {
    private final MCDownloadVersionList list = new MCDownloadVersionList();

    public VersionDownloadThread() {
    }

    @Override
    public void run() {
        try {
            list.startDownload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addObserver(IObserver<String> arg0) {
        list.addObserver(arg0);
    }

    @Override
    public void deleteObserver(IObserver<String> arg0) {
        list.deleteObserver(arg0);
    }

    @Override
    public void notifyObservers(String arg0) {
        throw new IllegalStateException("This is impossible.");
    }

    public MCDownloadVersionList getList(){
        return list;
    }
}
