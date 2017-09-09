package com.idp.engine.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.idp.engine.App;
import com.idp.engine.resources.assets.IdpColorPixmap;
import com.idp.engine.ui.graphics.base.Navbar;
import com.idp.engine.ui.screens.layers.MainLayer;
import com.idp.engine.ui.screens.layers.PopupLayer;

/**
 * Screen with navbar and layer structure.
 *
 *
 */
public class AppScreen extends IdpBaseScreen {

	private MainLayer mainLayer;
	private PopupLayer popupLayer;
	private Color navbarColor;
	private String name;
	private Actor fader;

	private boolean created = false;

	/**
	 * Calls {@link AppScreen#AppScreen(String, Color)} with empty name and white color.
	 */
	public AppScreen() {
		this("Screen");
	}

	/**
	 * Calls {@link AppScreen#AppScreen(String, Color)} with given name and white color.
	 * @param name screen name
	 */
	public AppScreen(String name) {
		this(name, App.Colors.MAIN);
	}

	/**
	 * Calls {@link AppScreen#AppScreen(String, Color)} with empty name and given color.
	 * @param navbarColor background color of the navbar
	 */
	public AppScreen(Color navbarColor) {
		this("Screen", navbarColor);
	}

	/**
	 * Constructs screen with given name(shown in navbar) and navbar colored with the given color.
	 * @param name name of the screen
	 * @param navbarColor background color of navbar
	 */
	public AppScreen(String name, Color navbarColor) {
		super(true);
		this.name = name;
		this.navbarColor = navbarColor;
		initStructure();
		//init();
	}


	/**
	 * @return navbar
	 */
	protected Navbar getNavbar() {
		return mainLayer.navbar;
	}

	/**
	 * @return main layer
	 */
	protected MainLayer getMainLayer() {
        return mainLayer;
    }



	/**
	 * @return popup layer
	 */
    public PopupLayer getPopupLayer() {
        return popupLayer;
    }

	/**
	 * Adds actor to the content of the screen.
	 * (Content is all-that-is-not-navbar).
	 * @param a actor to add
	 */
	public void addActor(Actor a) {
		mainLayer.content.addActor(a);
	}

	/**
	 * returns name of the screen that is shown in navbar.
	 * @return screen name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Fades screen with black semitransparent black.
	 * @param duration animation duration
	 */
	void fadeOut(float duration) {
		fader.getColor().a = 0;
		fader.addAction(Actions.alpha(0.4f, duration));
	}

	/**
	 * Removes black fade from the screen.
	 * @param duration animation duration
	 */
	public void fadeIn(float duration) {
		fader.getColor().a = 0.4f;
		fader.addAction(Actions.alpha(0f, duration));
	}

	public void init() {
		if (created) return;
		else created = true;
	}


	private void initStructure() {
		this.mainLayer = new MainLayer();
		mainLayer.navbar.setBackgroundColor(navbarColor);
		mainLayer.navbar.setTitle(name);
		stage.addActor(mainLayer);
		this.fader = new IdpColorPixmap(Color.WHITE).buildActor();
		//fader.setSize(mainLayer.getWidth(), mainLayer.getHeight());
		fader.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		fader.setColor(Color.CLEAR);
		fader.setTouchable(Touchable.disabled);
		stage.addActor(fader);

		this.popupLayer = new PopupLayer();
		stage.addActor(popupLayer);

		getMainLayer().addCaptureListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				stage.setKeyboardFocus(null);
				return false;
			}
		});
	}

    protected void clearScene() {
        stage.clear();
    }

	protected void hideNavBar() {
		getNavbar().setVisible(false);
		getMainLayer().content.setY(0);
		getMainLayer().content.setHeight(Gdx.graphics.getHeight());
	}



}
