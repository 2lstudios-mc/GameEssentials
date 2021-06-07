package dev._2lstudios.gameessentials.runnables;

import java.util.Collection;
import java.util.Iterator;

import dev._2lstudios.gameessentials.tasks.TeleportTask;

public class TeleportTaskRunnable implements Runnable {
    private final Collection<TeleportTask> teleportTasks;

    public TeleportTaskRunnable(final Collection<TeleportTask> teleportTasks) {
        this.teleportTasks = teleportTasks;
    }

    @Override
    public void run() {
        final Iterator<TeleportTask> teleportTaskIterator = teleportTasks.iterator();
        TeleportTask teleportTask;

        while (teleportTaskIterator.hasNext()) {
            teleportTask = teleportTaskIterator.next();
            if (teleportTask == null || teleportTask.tick()) {
                teleportTaskIterator.remove();
            }
        }
    }
}
