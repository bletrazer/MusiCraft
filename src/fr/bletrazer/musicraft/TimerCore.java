package fr.bletrazer.musicraft;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class TimerCore {

	private Boolean running;
	private Integer speed;
	private BukkitRunnable runnable;
	
	protected TimerCore() {

	}

	private void setDefaultRunnable() {
		this.setRunnable(new BukkitRunnable() {

			@Override
			public void run() {
				onScheduling(getRunning());
				
				if (!getRunning()) {
					stop();
				}
			}
		});
	}

	public Boolean start() {
		Boolean res = false;

		if (this.getRunning() == null || !this.getRunning()) {
			this.onStart();
			this.setRunning(true);
			this.setDefaultRunnable();
			this.getRunnable().runTaskTimer(Main.getInstance(), 0, this.getSpeed());
			res = true;

		}

		return res;
	}

	protected abstract void onStart();
	protected abstract void onStop();
	protected abstract void onScheduling(Boolean running);

	protected void stop() {
		this.onStop();
		this.setRunning(false);
		this.getRunnable().cancel();
	}

	public Boolean getRunning() {
		return running;
	}

	public void setRunning(Boolean running) {
		this.running = running;
	}

	public TimerCore getTimerCore() {
		return this;
	}

	public BukkitRunnable getRunnable() {
		return runnable;
	}

	public void setRunnable(BukkitRunnable runnable) {
		this.runnable = runnable;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

}
