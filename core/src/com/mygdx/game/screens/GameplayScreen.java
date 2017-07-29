package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.IClickCallback;
import com.mygdx.game.IShopCallback;
import com.mygdx.game.MyGame;
import com.mygdx.game.ShopItems;
import com.mygdx.game.controllers.FlyingObjectsController;
import com.mygdx.game.entites.Player;
import com.mygdx.game.ui.*;

import java.util.HashMap;

/**
 * Created by ares on 25.07.17.
 */
public class GameplayScreen extends BasicScreen {
    ScrollMenu welcomeMenu;
    Table shopContainer;
    private Image backgroundImg;
    private Player player;
    private FlyingObjectsController flyingObjectController;
    private GameLabel scoreLabel;
    private MenuButton menuButton;
    private OptionsButton optionsButton;
    private ScrollMenu gameMenu;
    private ScrollMenu optionsMenu;
    private Image iconSound;
    private Image iconMusic;
    private Button resetButton;
    private Label.LabelStyle menuLabelStyle;
    private SequenceAction onBuyActions;
    private java.util.Map<String, Boolean> itemToDisappear;
    private Preferences prefs;
    private boolean isWelcomeVideoAd;
    private long watchedAdBasaltBonus;
    private Array<Table> shopItems;
    private Label extraBonusLabel;
    private long basaltFirstRewardForVideoAd = 400;
    private int basaltMultiplyRewardForVideoAd; // 1 = 100%

    public GameplayScreen(MyGame myGame) {
        super(myGame);
    }


    @Override
    protected void init() {
        initBackground();
        initPlayer();
        handleClickArea();
        initOptionsButton();
        initShopMenuIcon();
        initShopMenu();
        initWelcomeWindow();
        initScoreLabel();
        initFlyingObjectController();
        initPassiveIncome();
        InitOptions();
        // TODO add clouds and events under them
    }

    private void initShopDataFromPrefs() {
        itemToDisappear = new HashMap<String, Boolean>();
        prefs = Gdx.app.getPreferences(MyGame.GAME_PREFS);
        for (ShopItems item : ShopItems.values()) {
            itemToDisappear.put(item.toString(), prefs.getBoolean(item.toString()));
        }
    }

    private void handleIconsListeners() {
        iconMusic.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (myGame.soundService.muteMusic) {
                    myGame.soundService.setMuteMusic(false);
                    iconMusic.setDrawable(optionsMenu.skinBlue.getDrawable("icon_music"));
                } else {
                    myGame.soundService.setMuteMusic(true);
                    iconMusic.setDrawable(optionsMenu.skinBlue.getDrawable("icon_pause"));
                }
                // TODO add INFO ON THE SCREEN ABOUT IT
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        iconSound.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (myGame.soundService.muteSound) {
                    myGame.soundService.setMuteSound(false);
                    iconSound.setDrawable(optionsMenu.skinBlue.getDrawable("icon_sound_on"));
                } else {
                    myGame.soundService.setMuteSound(true);
                    iconSound.setDrawable(optionsMenu.skinBlue.getDrawable("icon_sound_off"));
                }

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        backgroundImg.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (optionsMenu.isVisible())
                    optionsMenu.setVisible(false);
                if (gameMenu.isVisible())
                    gameMenu.setVisible(false);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    private void InitOptions() {
        optionsMenu = new ScrollMenu();
        optionsMenu.content.defaults().center();
        stage.addActor(optionsMenu);
        optionsMenu.setVisible(false);
        optionsMenu.setHeight(300);
        optionsMenu.setPosition(optionsMenu.getX(), optionsMenu.getY() + 100);
        handleAudioIcon();
        handleResetButton();

        handleIconsListeners();
    }

    private void handleResetButton() {
        resetButton = new Button(
                optionsMenu.skinBlue.getDrawable("button_01"),
                optionsMenu.skinBlue.getDrawable("button_02"));

        resetButton.add(new Label("Reset", new Label.LabelStyle(
                new BitmapFont(), Color.FIREBRICK)));

        optionsMenu.content.row().spaceTop(30);
        optionsMenu.content.add(new Label("tap few times on reset button hurriedly\nin order to reset score",
                new Label.LabelStyle(new BitmapFont(), Color.LIGHT_GRAY)));

        optionsMenu.content.row();
        optionsMenu.content.add(resetButton);
        handleResetButtonListener();
    }

    private void handleResetButtonListener() {
        resetButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (getTapCount() >= 4) {
                    resetGameData();
                }
                return super.touchDown(event, x, y, pointer, button);
            }

        });
    }

    private void resetGameData() {
        myGame.scoreService.resetScoreData();
        resetShopData();
    }

    private void resetShopData() {
        for (ShopItems item : ShopItems.values()) {
            itemToDisappear.put(item.toString(), false);
        }
        for (Table table : shopItems) {
            table.setVisible(true);
        }
    }

    private void handleAudioIcon() {
        if (myGame.soundService.muteSound)
            iconSound = new Image(optionsMenu.skinBlue.getDrawable("icon_sound_off"));
        else
            iconSound = new Image(optionsMenu.skinBlue.getDrawable("icon_sound_on"));

        if (myGame.soundService.muteMusic)
            iconMusic = new Image(optionsMenu.skinBlue.getDrawable("icon_pause"));
        else
            iconMusic = new Image(optionsMenu.skinBlue.getDrawable("icon_music"));

        optionsMenu.content.row();
        optionsMenu.content.add(iconMusic);
        optionsMenu.content.row();
        optionsMenu.content.add(iconSound);
    }

    private void initShopMenu() {

        gameMenu = new ScrollMenu();
        stage.addActor(gameMenu);
        gameMenu.setVisible(false);
        gameMenu.setStyle(new ScrollPane.ScrollPaneStyle(
                gameMenu.skinGray.getDrawable("button_03"),
                null, null,
                gameMenu.skinGray.getDrawable("knob_03"),
                gameMenu.skinGray.getDrawable("slider_back_ver")
        ));

        initShopContent();
    }


    private void initShopContent() {
        menuLabelStyle = new Label.LabelStyle(new BitmapFont(), Color.GOLD);
        shopContainer = new Table();

        addShopTitle();
        initShopDataFromPrefs();
        initAddingItems();

        gameMenu.content.add(shopContainer);

    }

    private void addShopTitle() {
        Label title = new Label("Tap few times hurriedly on item to buy",
                new Label.LabelStyle(new BitmapFont(), Color.RED));

        shopContainer.add(title).expand().fill();
        shopContainer.row();
    }


    private void initAddingItems() {
        shopItems = new Array<Table>();
        addExtraBonus();
        addBasaltDiamondExchange();
        addOfflineIncome();
        addWoodenPickaxe();
        addBasaltPickaxe();
        addSilverPickaxe();
        addDiamondPickaxe();
        addWorker();
        addSmallShaft();
        addBigShaft();
    }

    private void addExtraBonus() {
        watchedAdBasaltBonus = (long) (myGame.scoreService.getBasalt() * 0.35 + 5000);

        addItemToShop("img/helmet.png",
                "Watch ad and get \n+" + watchedAdBasaltBonus + " basalts!",
                new IShopCallback() {
                    @Override
                    public boolean isBuying() {
                        myGame.showVideoAd();
                        return true;
                    }
                }, ShopItems.EXTRA_BONUS);
    }

    private void addOfflineIncome() {
        final int cost = (int) (1 + myGame.scoreService.getOfflineBasaltIncome() * 5);
        final float bonus = 0.05f;

        addItemToShop("img/bed.png",
                "Increase offline basalt income by " + bonus + "%" +
                        "\ncost: " + cost + " diamond",
                new IShopCallback() {
                    @Override
                    public boolean isBuying() {
                        if (myGame.scoreService.getDiamonds() >= cost) {
                            myGame.scoreService.addToDiamonds(-cost);
                            myGame.scoreService.addToOfflinePassiveMultiply(bonus);
                            return true;
                        }
                        return false;
                    }
                }, ShopItems.OFFLINE_INCOME);
    }

    private void addBigShaft() {
        final int cost = 125000;
        final int bonus = 60;

        addItemToShop("img/mineShaftBig.png",
                "PROFESSIONAL MINING SHAFT\n+" + bonus + " basalt per second" +
                        "\ncost: " + cost + " B",
                new IShopCallback() {
                    @Override
                    public boolean isBuying() {
                        if (myGame.scoreService.getBasalt() >= cost) {
                            myGame.scoreService.addToBasalt(-cost);
                            myGame.scoreService.addToPassiveBasalt(bonus);
                            return true;
                        }
                        return false;
                    }
                }, ShopItems.BIG_SHAFT);
    }

    private void addSmallShaft() {
        final int cost = 9000;
        final int bonus = 10;

        addItemToShop("img/mineShaftSmall.png",
                "SMALL MINING SHAFT\n+" + bonus + " basalt per second" +
                        "\ncost: " + cost + " B",
                new IShopCallback() {
                    @Override
                    public boolean isBuying() {
                        if (myGame.scoreService.getBasalt() >= cost) {
                            myGame.scoreService.addToBasalt(-cost);
                            myGame.scoreService.addToPassiveBasalt(bonus);
                            return true;
                        }
                        return false;
                    }
                }, ShopItems.SILVER_PICKAXE);
    }

    private void addWorker() {
        final int cost = 200;
        final int bonus = 1;

        addItemToShop("img/worker.png",
                "worker\n+" + bonus + " basalt per second" +
                        "\ncost: " + cost + " B",
                new IShopCallback() {
                    @Override
                    public boolean isBuying() {
                        if (myGame.scoreService.getBasalt() > cost) {
                            myGame.scoreService.addToBasalt(-cost);
                            myGame.scoreService.addToPassiveBasalt(bonus);
                            return true;
                        }
                        return false;
                    }
                }, ShopItems.WORKER);
    }

    private void addDiamondPickaxe() {
        final int cost = 80000;
        final int bonus = 100;

        addItemToShop("img/diamondPickaxe.png",
                "DIAMOND PICKAXE\n+" + bonus + " basalt per click" +
                        "\ncost: " + cost + " B",
                new IShopCallback() {
                    @Override
                    public boolean isBuying() {
                        if (myGame.scoreService.getBasalt() >= cost) {
                            myGame.scoreService.addToBasalt(-cost);
                            myGame.scoreService.addToBasaltPerClick(bonus);
                            return true;
                        }
                        return false;
                    }
                }, ShopItems.DIAMOND_PICKAXE);
    }

    private void addSilverPickaxe() {
        final int cost = 5000;
        final int bonus = 10;

        addItemToShop("img/silverPickaxe.png",
                "SILVER PICKAXE\n+" + bonus + " basalt per click" +
                        "\ncost: " + cost + " B",
                new IShopCallback() {
                    @Override
                    public boolean isBuying() {
                        if (myGame.scoreService.getBasalt() >= cost) {
                            myGame.scoreService.addToBasalt(-cost);
                            myGame.scoreService.addToBasaltPerClick(bonus);
                            return true;
                        }
                        return false;
                    }
                }, ShopItems.SILVER_PICKAXE);

    }


    private void addBasaltPickaxe() {
        final int cost = 500;
        final int bonus = 5;

        addItemToShop("img/basaltPickaxe.png",
                "BASALT PICKAXE\n+" + bonus + " basalt per click" +
                        "\ncost: " + cost + " B",
                new IShopCallback() {
                    @Override
                    public boolean isBuying() {
                        if (myGame.scoreService.getBasalt() >= cost) {
                            myGame.scoreService.addToBasalt(-cost);
                            myGame.scoreService.addToBasaltPerClick(bonus);
                            return true;
                        }
                        return false;
                    }
                }, ShopItems.BASALT_PICKAXE);

    }

    private void addWoodenPickaxe() {
        final int woodenPickaxeCost = 150;
        final int bonus = 2;
        // TODO improve description
        addItemToShop("img/woodenPickaxe.png",
                "WOODEN PICKAXE\n+2 basalt per click\ncost: 150 B",
                new IShopCallback() {
                    @Override
                    public boolean isBuying() {
                        if (myGame.scoreService.getBasalt() >= woodenPickaxeCost) {
                            myGame.scoreService.addToBasalt(-woodenPickaxeCost);
                            myGame.scoreService.addToBasaltPerClick(bonus);
                            return true;
                        }
                        return false;
                    }
                }, ShopItems.WOODEN_PICKAXE);
    }

    private void addBasaltDiamondExchange() {
        // TODO improve description

        addItemToShop("img/diamond.png",
                "1000 basalt -> 1 diamond",
                new IShopCallback() {
                    @Override
                    public boolean isBuying() {
                        if (myGame.scoreService.getBasalt() >= 1000) {
                            myGame.scoreService.addToBasalt(-1000);
                            myGame.scoreService.addToDiamonds(1);
                            return true;
                        }
                        return false;
                    }
                }, ShopItems.DIAMOND);
    }

    private void addItemToShop(String imgPath, String description, final IShopCallback callback, final ShopItems itemName) {
        final Table table = new Table();
        table.add(new Label("", menuLabelStyle)).height(70).fill();
        table.add(new Image(new Texture(imgPath))).setActorHeight(50);
        final Label label = new Label("", menuLabelStyle);
        table.add(new Label("", menuLabelStyle)).width(10f).fillY();
        table.add(label).expand().fill();
        label.setText(description);

        table.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (getTapCount() >= 2) {
                    if (callback.isBuying()) {

                        table.addAction(new SequenceAction(
                                Actions.sizeBy(-30, -30, 0.5f, Interpolation.fade),
                                Actions.sizeBy(30, 30, 0.5f),
                                Actions.rotateBy(360, 1f)));

                        if (!(itemName.equals(ShopItems.DIAMOND) ||
                                itemName.equals(ShopItems.OFFLINE_INCOME) || itemName.equals(ShopItems.EXTRA_BONUS))) {
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    table.setVisible(false);
                                    itemToDisappear.put(itemName.toString(), true);
                                }
                            }, 1);
                        }

                    }

                }
                table.addAction(new SequenceAction(
                        Actions.sizeBy(2, 2, 0.5f),
                        Actions.sizeBy(-2, -2, 0.5f),
                        Actions.moveBy(-1, 1, 0.5f),
                        Actions.moveBy(1, -1, 0.5f)
                ));

                if (itemName.equals(ShopItems.EXTRA_BONUS))
                    extraBonusLabel = label;

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        if (itemToDisappear.get(itemName.toString())) {
            table.setVisible(false);
        }

        shopItems.add(table);
        shopContainer.add(table).expand().fill();
        shopContainer.row();
    }

    private void saveShopDataToPrefs() {
        for (ShopItems item : ShopItems.values()) {
            prefs.putBoolean(item.toString(), itemToDisappear.get(item.toString()));
        }
        prefs.flush();
    }

    @Override
    public void pause() {
        saveShopDataToPrefs();
        super.pause();
    }

    private void initOptionsButton() {
        optionsButton = new OptionsButton(new IClickCallback() {
            @Override
            public void onClick() {
                if (optionsMenu.isVisible())
                    optionsMenu.setVisible(false);
                else
                    optionsMenu.setVisible(true);
                gameMenu.setVisible(false);
            }
        });
        optionsButton.setPosition(420, 550);

        stage.addActor(optionsButton);
    }

    private void initShopMenuIcon() {
        menuButton = new MenuButton(new IClickCallback() {
            @Override
            public void onClick() {
                if (gameMenu.isVisible())
                    gameMenu.setVisible(false);
                else {
                    gameMenu.setVisible(true);
                    stage.setScrollFocus(gameMenu);
                }
                optionsMenu.setVisible(false);
            }
        }, "Shop");
        menuButton.setPosition(320, 550);
        menuButton.setWidth(80);
        stage.addActor(menuButton);

    }


    public void initWelcomeWindow() {
        welcomeMenu = new ScrollMenu();
        stage.addActor(welcomeMenu);

        Drawable buttonDraw = gameMenu.skinBlue.getDrawable("button_01");
        buttonDraw.setMinWidth(125);
        buttonDraw.setMinHeight(35);

        Button watchAdButton = new Button(
                new Button.ButtonStyle(buttonDraw,
                        gameMenu.skinBlue.getDrawable("button_02"),
                        buttonDraw));
        watchAdButton.addActor(new Label(" WATCH AD ",
                new Label.LabelStyle(new BitmapFont(), Color.GOLD)));

        Button noThanksButton = new Button(
                new Button.ButtonStyle(buttonDraw,
                        gameMenu.skinBlue.getDrawable("button_02"),
                        buttonDraw));
        noThanksButton.addActor(new Label("  NO, THANKS",
                new Label.LabelStyle(new BitmapFont(), Color.GOLD)));


        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY);
        Label label = new Label("\n\nOFFLINE BA$ALT INCOME: " + ((long) (myGame.scoreService.getOfflineBasaltIncome())) + "!\n" +
                "Watch ad to increase your offline income by 400%.",
                labelStyle);

        basaltMultiplyRewardForVideoAd = 4; // 4 == 400%;

        if (((long) (myGame.scoreService.getOfflineBasaltIncome()) <= 1) && myGame.scoreService.getBasalt() > 0) {
            basaltFirstRewardForVideoAd = myGame.scoreService.getBasalt() / 5 + 15;
            label.setText("\n\nWatch ad and get " + basaltFirstRewardForVideoAd + " basalts");
        }

        if (myGame.scoreService.getBasalt() == 0)
            label.setText("\n\nLooks like you need some help\nWatch ad and get " + basaltFirstRewardForVideoAd + " basalts\n");

        addWatchButtonHandler(watchAdButton);

        addExitWelcomeMenuHandler(welcomeMenu, noThanksButton);

        Table buttonTable = new Table();
        buttonTable.add(watchAdButton).pad(10);
        buttonTable.add(noThanksButton).pad(10);

        adjustWelcomeMenu(welcomeMenu);

        addActorsToWelcomeMenu(welcomeMenu, label, buttonTable);

        welcomeMenu.setVisible(false);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                welcomeMenu.setVisible(true);
            }
        },2f);

    }

    private void addActorsToWelcomeMenu(ScrollMenu welcomeMenu, Label label, Table buttonTable) {
        welcomeMenu.content.row();
        welcomeMenu.content.add(label).expand();
        welcomeMenu.content.row();
        welcomeMenu.content.add(buttonTable).expand().fill();
        stage.addActor(welcomeMenu);
    }

    private void adjustWelcomeMenu(ScrollMenu welcomeMenu) {
        welcomeMenu.setHeight(200);
        welcomeMenu.setWidth(MyGame.GAME_WIDTH - 50);
        welcomeMenu.setY(200);
        welcomeMenu.setX(25);
    }

    private void addExitWelcomeMenuHandler(final ScrollMenu welcomeMenu, Button noThanksButton) {
        noThanksButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                welcomeMenu.setVisible(false);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    private void addWatchButtonHandler(Button watchAdButton) {
        watchAdButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                myGame.showVideoAd();
                isWelcomeVideoAd = true;
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    private void initPassiveIncome() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                myGame.scoreService.addToBasalt(myGame.scoreService.getPassiveBasalt());
            }
        }, 0, 1f, Integer.MAX_VALUE);
    }

    private void initScoreLabel() {
        scoreLabel = new GameLabel(40, 550);
        stage.addActor(scoreLabel);
    }

    private void initFlyingObjectController() {
        flyingObjectController = new FlyingObjectsController(myGame, stage);

    }

    private void handleClickArea() {
        ClickOnPlayerButton clickOnPlayerButton = new ClickOnPlayerButton(new IClickCallback() {
            @Override
            public void onClick() {
                player.performClickEffect();
                myGame.soundService.playClick();
                myGame.scoreService.addBasaltForClick();
            }
        });
        stage.addActor(clickOnPlayerButton);
    }

    private void initPlayer() {
        player = new Player();
        stage.addActor(player);

    }

    private void initBackground() {
        backgroundImg = new Image(new Texture("img/backgroundImg2.png"));
        stage.addActor(backgroundImg);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        updateScore();
        updateExtraBonusLabel();
        addRewardForWelcomeAd();

        spriteBatch.begin();
        stage.draw();
        stage.act();
        spriteBatch.end();

    }

    private void updateExtraBonusLabel() {
        if (extraBonusLabel != null)
            extraBonusLabel.setText("Watch ad and get \n+" + watchedAdBasaltBonus + " basalts!");

    }

    private void addRewardForWelcomeAd() {
        if (MyGame.adShown && isWelcomeVideoAd) {
            MyGame.adShown = false;
            isWelcomeVideoAd = false;
            if (myGame.scoreService.getBasalt() == 0) {
                myGame.scoreService.addToBasalt(basaltFirstRewardForVideoAd);
            } else {
                myGame.scoreService.addToBasalt(
                        myGame.scoreService.getOfflineIncome() * basaltMultiplyRewardForVideoAd);
            }
            welcomeMenu.setVisible(false);
        } else if (MyGame.adShown) {
            MyGame.adShown = false;
            myGame.scoreService.addToBasalt(watchedAdBasaltBonus);
        }
    }

    private void updateScore() {
        scoreLabel.setText("Basalt(B):  " + myGame.scoreService.getBasalt() +
                "\n" + myGame.scoreService.getPassiveBasalt() + " basalt/sec" +
                "\nDiamonds: " + myGame.scoreService.getDiamonds() +
                "\nB per click: " + myGame.scoreService.getBasaltPerClick() +
                "\nOffline B income: " +
                String.format("%.2f", myGame.scoreService.getOfflineBasaltIncome())
                + "/sec");
    }

}
