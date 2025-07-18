package scene.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Class BirdAnimation
 */
public class BirdAnimation {

    private final Pane pane;
    private ImageView bird;
    private TranslateTransition flyRightAnimation;
    private TranslateTransition flyLeftAnimation;

    /**
     * Constructs a new bird animation controller.
     *
     * @param pane the pane
     */
    public BirdAnimation(Pane pane) {
        this.pane = pane;
    }

    /**
     * Sets up the bird image and initializes the animation sequences
     */
    public void setupAnimation() {
        Image birdImage = new Image(getClass().getResourceAsStream("/test_scenes/flying-bird.gif"));
        bird = new ImageView(birdImage);

        bird.setFitWidth(100);
        bird.setFitHeight(100);
        bird.setX(-110);
        bird.setY(30);

        pane.getChildren().add(bird);

        createFlyRightAnimation();
        createFlyLeftAnimation();

        flyRightAnimation.setOnFinished(event -> {
            bird.setScaleX(-1);
            flyLeftAnimation.play();
        });

        flyLeftAnimation.setOnFinished(event -> {
            bird.setScaleX(1);
            flyRightAnimation.play();
        });
    }

    /**
     * Creates the animation for the bird flying from left to right
     */
    private void createFlyRightAnimation() {
        flyRightAnimation = new TranslateTransition();
        flyRightAnimation.setNode(bird);
        flyRightAnimation.setDuration(Duration.seconds(12));
        flyRightAnimation.setFromX(-110);
        flyRightAnimation.setToX(900);
    }

    /**
     * Creates the animation for the bird flying from right to left
     */
    private void createFlyLeftAnimation() {
        flyLeftAnimation = new TranslateTransition();
        flyLeftAnimation.setNode(bird);
        flyLeftAnimation.setDuration(Duration.seconds(12));
        flyLeftAnimation.setFromX(900);
        flyLeftAnimation.setToX(-110);
    }

    /**
     * Starts the bird animation sequence
     */
    public void startAnimation() {
        flyRightAnimation.play();
    }
}
