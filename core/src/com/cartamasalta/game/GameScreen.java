package com.cartamasalta.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cartamasalta.game.domain.Card;
import com.cartamasalta.game.domain.Deck;
import com.cartamasalta.game.domain.Player;

import java.util.ArrayList;
import java.util.List;


public class GameScreen extends ScreenAdapter {

    private final App app;
    private final OrthographicCamera guiCam;
    private final Deck deck;
    private final Player[] players;
    private final Vector3 touchPoint = new Vector3();

    private Player turnStarter;
    private Player turn;

    private Card triunfalCard;


    //Testing
    private boolean thinking = false;
    private boolean roundEnded = false;
    private Stage stage;
    private Stage stage2;
    private Label notificationLabel;


    public static final Rectangle player1Rec = new Rectangle(
            10 + 75 / 2 - 20,
            App.HEIGHT - 125,
            40,
            70);

    public GameScreen(App app) {
        this.app = app;
        deck = new Deck(Assets.cards);

        guiCam = new OrthographicCamera(App.WIDTH, App.HEIGHT);
        guiCam.position.set(App.WIDTH / 2, App.HEIGHT / 2, 0);

        players = GameUtils.getPlayers();

        triunfalCard = deck.takeCard();
        System.out.println("triunfal Card is " + triunfalCard.getType());
        for (int i = 0; i < 3; i++) {
            players[0].addCard(deck.takeCard());
            players[1].addCard(deck.takeCard());
            players[2].addCard(deck.takeCard());
            players[3].addCard(deck.takeCard());
        }

        turn = players[0];
        turnStarter = turn;
        //labels[0]=
        FitViewport viewport = new FitViewport(App.WIDTH, App.HEIGHT, guiCam);
        stage = new Stage(viewport, app.batch);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(2);
        notificationLabel = new Label("", new Label.LabelStyle(font, Color.BLUE));
        stage2 = new Stage(viewport, app.batch);
        stage2.addActor(notificationLabel);


        players[3].getPointsLabel().setAlignment(Align.right);
        Table table = new Table();
        table.top();
        table.setPosition(App.WIDTH / 2, App.HEIGHT - 70);
        table.add();
        table.add(players[2].getPointsLabel()).align(Align.topLeft).height(30);
        table.row();
        table.add().width(60).height(220);
        table.row();
        table.add(players[1].getPointsLabel()).width(60);
        table.add().width(130);
        table.add(players[3].getPointsLabel()).width(110);
        table.row();
        table.add().height(50);
        table.row();
        table.add();
        table.add(players[0].getPointsLabel()).align(Align.topLeft).height(30);
        table.row();

        stage.addActor(table);

        updateNotificationLabel("You can start the round");
    }

    private Player nextPlayer(Player from) {
        boolean found = false;
        for (Player player : players) {
            if (found) {
                return player;
            }
            if (from == player) {
                found = true;
            }
        }
        return players[0];
    }

    private Player nextTurn() {
        Player player = nextPlayer(turn);
        if (player.equals(turnStarter)) {
            roundEnded = true;
        }
        return player;
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }

    private void update() {
        checkEndRound();
        if (roundEnded) {
            return;
        }
        updateNotificationLabel(turn.getName() + " turn");
        if (Gdx.input.justTouched() && turn == players[0]) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            Card selectedCard = null;
            if (getRectangle(-50).contains(touchPoint.x, touchPoint.y)) {
                selectedCard = players[0].getCards().get(0);
            } else if (getRectangle(0).contains(touchPoint.x, touchPoint.y)) {
                selectedCard = players[0].getCards().get(1);
            } else if (getRectangle(50).contains(touchPoint.x, touchPoint.y)) {
                selectedCard = players[0].getCards().get(2);
            }
            if (selectedCard != null) {
                turn = nextTurn();
                players[0].setSelectedCard(selectedCard);
                selectedCard.setSelected(true);
            }

            updateNotificationLabel("");

        }
        logicAI();
    }

    private void logicAI() {
        if (turn != players[0] && !thinking) {
            thinking = true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //IA Logic taking a card
                    int cardIndex = Utils.getRandomNumberInRange(0, 2);
                    Card card = turn.getCards().get(cardIndex);
                    turn.setSelectedCard(card);
                    card.setSelected(true);
                    turn = nextTurn();
                    thinking = false;
                }
            }, Utils.getRandomNumberInRange(1, 10));
        }
    }


    private Card getWinnerCard(Card card1, Card card2) {
        if (card1.getType().equals(triunfalCard.getType()) &&
                !card2.getType().equals(triunfalCard.getType())) {
            return card1;
        }
        if (!card1.getType().equals(triunfalCard.getType()) &&
                card2.getType().equals(triunfalCard.getType())) {
            return card2;
        }
        if (!card1.getType().equals(card2.getType())) {
            return card1;
        }

        if (ValueMapper.getValue(card1) > ValueMapper.getValue(card2)) {
            return card1;
        }
        return card2;
    }

    private void calculateRound() {
        Card winner = turn.getSelectedCard();
        Player current = turn;
        int points = 0;
        do {
            Card card = current.getSelectedCard();
            System.out.println("Card by " + card.getOwner().getName() + " " + card.getType() + " " + card.getValue());
            System.out.println("Winner " + winner.getValue() + " " + winner.getType() + " Vs " + " Card " + card.getValue() + " " + card.getType());
            winner = getWinnerCard(winner, card);
            System.out.println("Wins " + winner.getValue() + " " + winner.getType());
            points += ValueMapper.getPoints(card);

            current = nextPlayer(current);
        } while (!current.equals(turn));

        notificationLabel.setText(winner.getOwner().getName() + " wins the round");
        notificationLabel.setPosition(App.WIDTH / 2 - notificationLabel.getWidth() / 2, App.HEIGHT / 2 - 100);
        updateNotificationLabel(winner.getOwner().getName() + " wins the round!");
        System.out.println("Winner" + winner.getOwner().getName() + " " + points);
        winner.getOwner().addPoints(points);
        System.out.println("Remaining cards " + deck.getCounter());
        System.out.println("New round");

        if (deck.moreCards()) {
            turn = winner.getOwner();
            turnStarter = turn;

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //Start new turn
                    for(Player player : players){
                        Card card = player.getSelectedCard();
                        player.getCards().remove(card);
                        if (deck.moreCards()) {
                            player.addCard(deck.takeCard());
                        } else {
                            player.addCard(triunfalCard);
                        }
                    }
                    roundEnded = false;
                    thinking = false;
                }
            },3);


        } else {
            updateNotificationLabel("Game is done!");
        }

    }

    private void checkEndRound() {
        if (!roundEnded || thinking) {
            return;
        }
        thinking = true;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                calculateRound();
            }
        }, Utils.getRandomNumberInRange(1, 10));
    }

    private void checkEndGame() {

    }

    private void draw() {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        guiCam.update();
        app.batch.setProjectionMatrix(guiCam.combined);
        app.batch.enableBlending();
        app.batch.begin();
        app.batch.draw(Assets.background, 0, 0, App.WIDTH, App.HEIGHT);
        app.batch.end();

        List<Sprite> sprites = new ArrayList();
        sprites.add(getSpriteSouth(players[0].getCards().get(0), -50));
        sprites.add(getSpriteSouth(players[0].getCards().get(1), 0));
        sprites.add(getSpriteSouth(players[0].getCards().get(2), 50));

        sprites.add(getSpriteWest(players[1].getCards().get(0), -50));
        sprites.add(getSpriteWest(players[1].getCards().get(1), 0));
        sprites.add(getSpriteWest(players[1].getCards().get(2), 50));

        sprites.add(getSpriteNorth(players[2].getCards().get(0), -50));
        sprites.add(getSpriteNorth(players[2].getCards().get(1), 0));
        sprites.add(getSpriteNorth(players[2].getCards().get(2), 50));

        sprites.add(getSpriteEst(players[3].getCards().get(0), -50));
        sprites.add(getSpriteEst(players[3].getCards().get(1), 0));
        sprites.add(getSpriteEst(players[3].getCards().get(2), 50));

        app.batch.begin();
        app.batch.draw(Assets.cardBack, Deck.position.x, Deck.position.y, Deck.position.width, Deck.position.height);
        app.batch.draw(triunfalCard.getImage(), Deck.position.x + 50, Deck.position.y, Deck.position.width, Deck.position.height);


        for (Sprite sprite : sprites) {
            sprite.draw(app.batch);
        }

        app.batch.end();

        stage.draw();
        stage2.draw();
    }

    private Sprite getSpriteSouth(Card card, float x) {
        int y = 0;
        if (card.isSelected()) {
            y = 50;
        }
        return getSprite(card.getImage(), App.WIDTH / 2 - Card.CARD_WIDTH / 2 + x, y, 0);
    }

    private Sprite getSpriteNorth(Card card, float x) {
        return getSprite(card.getImage(), App.WIDTH / 2 - Card.CARD_WIDTH / 2 + x, App.HEIGHT - Card.CARD_HEIGHT, 180);
    }

    private Sprite getSpriteWest(Card card, float x) {
        return getSprite(card.getImage(), Card.CARD_WIDTH / 2, App.HEIGHT / 2 - Card.CARD_HEIGHT / 2 + x, 270);
    }

    private Sprite getSpriteEst(Card card, float x) {
        return getSprite(card.getImage(), App.WIDTH - Card.CARD_WIDTH / 2 * 3, App.HEIGHT / 2 - Card.CARD_HEIGHT / 2 + x, 90);
    }

    private Sprite getSprite(TextureRegion image, float x, float y, int rotate) {
        Sprite sprite = new Sprite(image, 0, 0, 208, 319);
        sprite.setPosition(x, y);
        sprite.setSize(40, 64);
        sprite.setOriginCenter();
        sprite.setRotation(rotate);

        return sprite;
    }

    private Rectangle getRectangle(int x) {
        return new Rectangle(
                App.WIDTH / 2 - Card.CARD_WIDTH / 2 + x,
                0,
                Card.CARD_WIDTH,
                Card.CARD_HEIGHT);
    }


    private void updateNotificationLabel(String text) {
        notificationLabel.setText(text);
        notificationLabel.setPosition(App.WIDTH / 2 - getLabelWidth(text) / 2, App.HEIGHT / 2 + 120);
    }

    private float getLabelWidth(String text) {
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2);
        Label label = new Label(text, new Label.LabelStyle(font, Color.BLUE));
        return label.getWidth();
    }
}

