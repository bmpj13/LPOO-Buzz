package com.buzzit.GUI.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;

public class MultiplayerSettingsScreen extends SuperScreen {
    private Stage stage;

    private Skin skin;
    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private Pixmap cursorPixmap;
    private Pixmap whitePixmap;
    private Texture playTexture;
    private Texture cursorTexture;
    private Texture whiteTexture;
    private Texture checkedBoxTexture;
    private Texture uncheckedBoxTexture;
    private Texture categoriesBackgroundTexture;
    static private ArrayList<CheckBox> checkBoxes;
    static private TextField numQuestionsTextField;
    static private ArrayList<Label> checkBoxLabels;

    MultiplayerSettingsScreen(Game g, ScreenState.ScreenType pType) {
        create();
        this.game = g;
        this.parentType = pType;
    }

    @Override
    protected void create() {
        super.create();

        createSkin();

        /* User name */
        Label ConnectionLabel = new Label("CONNECTED!!", skin);

        /* Number of questions per game */
        Label numQuestionsLabel = new Label("Number of Questions", skin);
        numQuestionsTextField = new TextField("10", skin);
        numQuestionsTextField.setBlinkTime(1f);
        numQuestionsTextField.setAlignment(Align.center);
        numQuestionsTextField.setMaxLength(2);
        numQuestionsTextField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());


        final int smallPad = Gdx.graphics.getHeight()/60;
        final int bigPad = Gdx.graphics.getHeight()/12;

        /* Categories wanted */
        checkBoxLabels = new ArrayList<>();
        checkBoxes = new ArrayList<>();
        Label categoriesLabel = new Label("Categories", skin);
        Label categoryNameLabel = new Label("SPORTS", skin, "categoryName");
        CheckBox checkBox1 = new CheckBox("", skin);
        checkBoxes.add(checkBox1);
        checkBoxLabels.add(categoriesLabel);

        Table categoriesTable = new Table();
        categoriesTable.add(categoryNameLabel).pad(smallPad, smallPad, 0, 0);
        categoriesTable.add(checkBox1).pad(smallPad, smallPad, smallPad/2, smallPad);
        categoriesTable.row();

        categoryNameLabel = new Label("MUSIC", skin, "categoryName");
        CheckBox checkBox2 = new CheckBox("", skin);
        checkBoxes.add(checkBox2);
        checkBoxLabels.add(categoryNameLabel);

        categoriesTable.add(categoryNameLabel).pad(0, smallPad, smallPad, 0);
        categoriesTable.add(checkBox2).pad(0, smallPad, smallPad, smallPad);

        categoriesBackgroundTexture = new Texture(Gdx.files.internal("settings/categories_background.png"));
        NinePatch patch = new NinePatch(categoriesBackgroundTexture, 3, 3, 3, 3);
        categoriesTable.setBackground(new NinePatchDrawable(patch));


        /* Difficulty */
        Label difficultyLabel = new Label("Difficulty", skin);
        String[] s = new String[] {"William Homo", "Joao Giro"};
        SelectBox<String> difficultySelectBox = new SelectBox<String>(skin);
        difficultySelectBox.setItems(s);

        /* next button */
        playTexture = new Texture(Gdx.files.internal("menu/play.png"));
        ImageButton btnPlay = new ImageButton(new SpriteDrawable(new Sprite(playTexture)));
        btnPlay.getImage().setScaling(Scaling.fit);

        // Main table
        Table table = new Table();

        table.add(ConnectionLabel).padBottom(smallPad).row();

        table.add(numQuestionsLabel).padBottom(smallPad).row();
        table.add(numQuestionsTextField).padBottom(bigPad).row();

        table.add(categoriesLabel).padBottom(smallPad).row();
        table.add(categoriesTable).padBottom(bigPad).row();

        table.add(difficultyLabel).padBottom(smallPad).row();
        table.add(difficultySelectBox).row();

        table.add(btnPlay).width(200).height(200).padTop(100);

        table.setFillParent(true);

        stage = new Stage();
        stage.addActor(table);

        /* Listeners */

        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ScreenState.getInstance().changeState(ScreenState.ScreenType.MULTIPLAYER2);

            }
        });
    }

    private void createSkin() {
        skin = new Skin();


        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/good_times.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;

        font = generator.generateFont(parameter);
        skin.add("default", font);

        cursorPixmap = new Pixmap((int) font.getSpaceWidth()/2, (int) font.getCapHeight(), Pixmap.Format.RGBA8888);
        cursorPixmap.setColor(Color.CORAL);
        cursorPixmap.fill();
        cursorTexture = new Texture(cursorPixmap);
        skin.add("cursorTexture", cursorTexture);

        whitePixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        whitePixmap.setColor(Color.WHITE);
        whitePixmap.fill();
        whiteTexture = new Texture(whitePixmap);
        skin.add("whiteBackground", whiteTexture);


        // Textfield Styles
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.fontColor = Color.BLACK;
        textFieldStyle.cursor = skin.newDrawable("cursorTexture");
        textFieldStyle.background = skin.newDrawable("whiteBackground");
        skin.add("default", textFieldStyle);

        // Label styles
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        labelStyle.fontColor = Color.CORAL;
        skin.add("default", labelStyle);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        labelStyle.fontColor = Color.WHITE;
        skin.add("categoryName", labelStyle);


        // Checkbox styles
        checkedBoxTexture = new Texture(Gdx.files.internal("settings/checked_checkbox.png"));
        uncheckedBoxTexture = new Texture(Gdx.files.internal("settings/unchecked_checkbox.png"));
        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.checkboxOn = new SpriteDrawable(new Sprite(checkedBoxTexture));
        checkBoxStyle.checkboxOff = new SpriteDrawable(new Sprite(uncheckedBoxTexture));
        checkBoxStyle.font = skin.getFont("default");
        skin.add("default", checkBoxStyle);


        // Selectbox styles
        List.ListStyle listStyle = new List.ListStyle();
        listStyle.font = skin.getFont("default");
        listStyle.fontColorUnselected = Color.CORAL;
        listStyle.fontColorSelected = Color.BLUE;
        listStyle.selection = skin.newDrawable("whiteBackground");

        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();

        SelectBox.SelectBoxStyle selectBoxStyle = new SelectBox.SelectBoxStyle();
        selectBoxStyle.font = skin.getFont("default");
        selectBoxStyle.fontColor = Color.CORAL;
        selectBoxStyle.listStyle = listStyle;
        selectBoxStyle.scrollStyle = scrollPaneStyle;
        selectBoxStyle.background = skin.newDrawable("whiteBackground");
        skin.add("default", selectBoxStyle);
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {
        super.hide();
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
        font.dispose();
        stage.dispose();
        cursorPixmap.dispose();
        cursorTexture.dispose();
        whitePixmap.dispose();
        whiteTexture.dispose();
        checkedBoxTexture.dispose();
        uncheckedBoxTexture.dispose();
        categoriesBackgroundTexture.dispose();
        generator.dispose();
    }

    public static ArrayList<String> getCategories(){
        ArrayList<String> c = new ArrayList<>();
        Gdx.app.log("getCategories", "before loop");
        for(int i = 0; i < checkBoxes.size(); i++){
            if(checkBoxes.get(i).isChecked()){
                c.add(checkBoxLabels.get(i).getText().toString());
            }
        }
        if(c.get(0).toString().equals("")){
            Gdx.app.log("UEUEUEUEUEUEUEUEUE", "empty");
        }

        Gdx.app.log("arraysize to return", Integer.toString(c.size()));
        Gdx.app.log("arrayCategory", c.get(0).toString());
        return c;
    }

    public static int getNumQuestions(){
        return Integer.parseInt(numQuestionsTextField.getText().toString());
    }
}