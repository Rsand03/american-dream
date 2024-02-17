package helper;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ee.taltech.americandream.AmericanDream;

public class Hud {
    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //score/time Tracking Variables
    public Integer worldTimer;
    private float timeCount;
    private static Integer score;

    //labels to be displayed on the hud
    private Label countdownLabel;
    private Label timeLabel;

    private Label firstPlayerLabel;
    private Label firstHealth;
    private Label secondPlayerLabel;
    private Label secondHealth;

    public Hud(SpriteBatch spritebatch) {

        //define our tracking variables
        // worldTimer can be changed while retaining correct formatting
        worldTimer = 300;
        int minutes = Math.floorDiv(worldTimer, 60);
        int seconds = worldTimer % minutes;


        //setup the HUD viewport using a new camera separate from our main game camera
        //define stage using that viewport and our games spritebatch
        viewport = new FitViewport(AmericanDream.INSTANCE.screenWidth, AmericanDream.INSTANCE.screenHeight, new OrthographicCamera());
        stage = new Stage(viewport, spritebatch);

        //define a table used to organize the hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define labels
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        countdownLabel = new Label( minutes + ":" + String.format("%02d", seconds),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // combining player name and heath label by using \n could make alignment easier
        firstPlayerLabel = new Label("TRUMP", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        firstHealth = new Label("  HP  16%", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        secondPlayerLabel = new Label("BIDEN   ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        secondHealth = new Label("HP  99%", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //add labels to our table, padding the top, and giving them all equal width with expandX
        table.add(firstPlayerLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.add(secondPlayerLabel).expandX().padTop(10);
        //add a second row to the table
        table.row();
        table.add(firstHealth).expandX();
        table.add(countdownLabel).expandX();
        table.add(secondHealth).expandX();

        //add table to the stage
        stage.addActor(table);
    }
    public void update(float deltaTime) {
        // calculate time based on frames rendered
        timeCount += deltaTime;
        if (timeCount >= 1 && worldTimer >= 1) {
            worldTimer--;
            int minutes = Math.floorDiv(worldTimer, 60);
            int seconds = worldTimer % 60;
            countdownLabel.setText(minutes + ":" + String.format("%02d", seconds));
            timeCount = 0;
        }
    }
}
