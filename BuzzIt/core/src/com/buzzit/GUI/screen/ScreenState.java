package com.buzzit.GUI.screen;

import com.badlogic.gdx.Game;

public class ScreenState {
    private static Game game;
    private static MenuScreen menu;
    private static SingleplayerScreen singlePlayer;
    private static SettingsScreen settingsScreen;

    private static ScreenState ourInstance = new ScreenState();

    public static ScreenState getInstance() { return ourInstance; }


    public enum ScreenType { MENU, SINGLEPLAYER, SETTINGS };


    private ScreenState() {
        menu = new MenuScreen(game, null);
        singlePlayer = new SingleplayerScreen(game, ScreenType.MENU);
        settingsScreen = new SettingsScreen(game, ScreenType.MENU);
    }

    public void setGame(Game g) {
        game = g;
    }

    public void changeState(ScreenType screenType) {

        switch (screenType) {
            case MENU:
                game.setScreen(menu);
                break;

            case SINGLEPLAYER:
                game.setScreen(singlePlayer);
                break;

            case SETTINGS:
                game.setScreen(settingsScreen);
                break;
        }
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        menu.dispose();
        singlePlayer.dispose();
        settingsScreen.dispose();
    }
}
