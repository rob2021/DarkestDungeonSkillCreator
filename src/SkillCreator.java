import java.awt.Toolkit;
import javafx.scene.control.Label;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ComboBox;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.*;
import java.awt.datatransfer.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.*;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;

public class SkillCreator extends Application {
	private Group root;
	private Scene scene;
	
	private TextField skillNameTextField = new TextField();
	private Label skillNameLabel = new Label("Skill Name: ");
	private ComboBox<String> damageTypeCombo = new ComboBox<String>();
	private ComboBox<String> targetTypeCombo = new ComboBox<String>();
	private Label damageTypeLabel = new Label("Damage Type: ");
	private CheckBox isCritValidCheck = new CheckBox("is Crit Valid?");
	
	private GridPane damageGridPane = new GridPane();
	private GridPane healGridPane = new GridPane();
	
	private TextField attackTextField = new TextField("90%");
	private Label attackLabel = new Label("Accuracy: ");
	private TextField damageTextField = new TextField("15%");
	private Label damageLabel = new Label("Damage: ");
	private TextField critTextField = new TextField("1%");
	private Label critLabel = new Label("Crit: ");
	
	private TextField healHighTextField = new TextField("6");
	private Label healHighLabel = new Label("Heal High: ");
	private TextField healLowTextField = new TextField("5");
	private Label healLowLabel = new Label("Heal Low: ");
	
	private Label playerLabel = new Label("Player Position:");
    private RadioButton player1 = new RadioButton("");
    private RadioButton player2 = new RadioButton("");
    private RadioButton player3 = new RadioButton("");
    private RadioButton player4 = new RadioButton("");
    private HBox playerHBox = new HBox();
    
    private RadioButton enemy1 = new RadioButton("");
    private RadioButton enemy2 = new RadioButton("");
    private RadioButton enemy3 = new RadioButton("");
    private RadioButton enemy4 = new RadioButton("");
    private HBox enemyHBox = new HBox();
    
	private Label targetLabel = new Label("Target: ");
	private HBox targetHBox = new HBox();
	
	private Label addEffectsLabel = new Label("Add Effects: ");
	private TextField effect1TextField = new TextField("Bleed 1");
	private TextField effect2TextField = new TextField("Vestal Stun 1");
	private TextField effect3TextField = new TextField();
	private TextField effect4TextField = new TextField();
	private TextField effect5TextField = new TextField();
	private VBox effectsVBox = new VBox();
	
	private CheckBox invalidingCheck = new CheckBox("Is stall invaliding?");
	private CheckBox generationCheck = new CheckBox("Is generation guaranteed?");
	private CheckBox multipleTargetsCheck = new CheckBox("AOE Skill?");
	private CheckBox incrEffectsCheck = new CheckBox("Increment all" + '\n'+ "level 1 effects?");
	private Button helpButton = new Button("?");
	private Button copyButton = new Button("Copy to Clipboard");
	
	private TextField moveForwardTextField = new TextField("0");
	private TextField moveBackwardTextField = new TextField("0");
	private Label moveBackwardLabel = new Label("Move Backwards");
	private Label moveForwardLabel = new Label("Move Forwards");
	private Label skillBattleLimitLabel = new Label("Limited uses per battle:" + '\n' + "        (0 unlimited)");
	private TextField skillBattleLimitTextField = new TextField("0");
	
	private String fullSkillCode = "";
	private String tempGetAttackStyle;
	
	private Label successLabel = new Label("Success!");
	private String errorStatus = "";
	private Label errorLabel = new Label("Error required info: " + errorStatus);
	
	private TextField attackPerLevelTextField = new TextField("5");
	private TextField damagePerLevelTextField = new TextField("0");
	private TextField critPerLevelTextField = new TextField("1");
	private TextField healLowPerLevelTextField = new TextField("1");
	private TextField healHighPerLevelTextField = new TextField("1");
	
	private GridPane dpsPerLevelGridPane = new GridPane();
	private GridPane healPerLevelGridPane = new GridPane();
	
	private Label perLevelLabel = new Label("Per Level");
	private Boolean string1Boolean = false;
	private Boolean string2Boolean = false;
	private Boolean string3Boolean = false;
	private Boolean string4Boolean = false;
	private Boolean string5Boolean = false;
	
	private String effect1StringNoNum = "";
	private String effect2StringNoNum = "";
	private String effect3StringNoNum = "";
	private String effect4StringNoNum = "";
	private String effect5StringNoNum = "";
	
	
	public static void main(String[] args) {
	    launch(args);
	    
	}
	
	@Override
	   public void start(Stage primaryStage) throws Exception {
		
		///////////////Visible
		isCritValidCheck.setVisible(true);
		damageGridPane.setVisible(true);
		healGridPane.setVisible(false);

		
		///////////////Primary scene
		root = new Group();
		scene = new Scene(root, 557.0, 620.0, Color.MIDNIGHTBLUE);
		primaryStage.setTitle("Darkest Dungeon Skill Creator");
		
		damageTypeCombo.getItems().addAll("Melee", "Range", "Heal");
		damageTypeCombo.getSelectionModel().selectFirst();
		
		targetTypeCombo.getItems().addAll("Enemy", "Allies", "Self");
		targetTypeCombo.getSelectionModel().selectFirst();
		incrEffectsCheck.setSelected(true);
		
		
		//////Help Button info
		infoButtonProperties(helpButton);
		////////////////
		
		
		copyButton.setOnAction(e -> {
			fullSkillCode = "";
			
			string1Boolean = false;
			string2Boolean = false;
			string3Boolean = false;
			string4Boolean = false;
			string5Boolean = false;
		
				if(!skillNameTextField.getText().toString().matches("")) {
					if((!player1.isSelected()) && (!player2.isSelected()) && (!player3.isSelected()) && (!player4.isSelected())) {
						//no player selected
		   				displayStatus("Player Position Selected", 5000, false, successLabel, errorLabel);
					} else {
						if((!enemy1.isSelected() && !enemy2.isSelected() && !enemy3.isSelected() && !enemy4.isSelected()) && (targetTypeCombo.getValue().toString() == "Enemy" || targetTypeCombo.getValue().toString() == "Allies")) {
							//no enemy/ally selected
			   				displayStatus("Enemy/Ally Selected", 5000, false, successLabel, errorLabel);
						} else {
							
							tempGetAttackStyle = damageTypeCombo.getValue().toString();
							tempGetAttackStyle = tempGetAttackStyle.toLowerCase();
							
							fullSkillCode = fullSkillCode + "combat_skill: .id \"" + skillNameTextField.getText().toString() + "\" .level 0";
							String tempAttackTextField = null;
							String tempDamageTextField = null;
							String tempCritTextField = null;
							String tempHealLow = null;
							String tempHealHigh = null; 
							
							if(damageTypeCombo.getValue().toString() == "Heal") {
								//Heal 
								tempHealLow = healLowTextField.getText().toString();
								tempHealHigh = healHighTextField.getText().toString();
								
								tempHealLow = tempHealLow.replaceAll("%", "");
								tempHealHigh = tempHealHigh.replaceAll("%", "");
								
								fullSkillCode = fullSkillCode + " .heal ";
								
								fullSkillCode = fullSkillCode + tempHealLow + " " + tempHealHigh;
								
							} else {
								//DPS
								tempAttackTextField = attackTextField.getText().toString();
								tempDamageTextField = damageTextField.getText().toString();
								tempCritTextField = critTextField.getText().toString();
								
								tempAttackTextField = tempAttackTextField.replaceAll("%", "");
								tempDamageTextField = tempDamageTextField.replaceAll("%", "");
								tempCritTextField = tempCritTextField.replaceAll("%", "");
								fullSkillCode = fullSkillCode + " .type \"" + tempGetAttackStyle + "\" .atk " + tempAttackTextField + "% .dmg " + tempDamageTextField + "% .crit " + tempCritTextField + "%";
								
							}
							
							// Move character option
							if((!moveBackwardTextField.getText().toString().matches("") && !moveBackwardTextField.getText().toString().matches("0")) || (!moveForwardTextField.getText().toString().matches("") && !moveForwardTextField.getText().toString().matches("0"))) {
								String tempForward = moveForwardTextField.getText().toString();
								String tempBackward = moveBackwardTextField.getText().toString();
								if(tempBackward.matches("")) {
									tempBackward = "0";
								}
								if(tempForward.matches("")) {
									tempForward = "0";
								}
								
								fullSkillCode = fullSkillCode + " .move " + tempBackward + " " + tempForward;
							}
							/////
							
							if(!skillBattleLimitTextField.getText().toString().matches("") && !skillBattleLimitTextField.getText().toString().matches("0")) {
								fullSkillCode = fullSkillCode + " .per_battle_limit " + skillBattleLimitTextField.getText().toString();
							}
							
							fullSkillCode = fullSkillCode + " .launch ";
							
							if(player4.isSelected()) {
								fullSkillCode = fullSkillCode + "4";
							}
							if(player3.isSelected()) {
								fullSkillCode = fullSkillCode + "3";
							}
							if(player2.isSelected()) {
								fullSkillCode = fullSkillCode + "2";
							}
							if(player1.isSelected()) {
								fullSkillCode = fullSkillCode + "1";
							}
							
							fullSkillCode = fullSkillCode + " .target";
							
							//~@1234
							if(targetTypeCombo.getValue().toString() == "Self") {
							
							
							} else {
								fullSkillCode = fullSkillCode + " ";
								
								
									if(multipleTargetsCheck.isSelected()) {
										fullSkillCode = fullSkillCode + "~";
									}
									
									if(targetTypeCombo.getValue().toString() == "Allies") {
										fullSkillCode = fullSkillCode + "@";
									}
									
									if(enemy1.isSelected()) {
										fullSkillCode = fullSkillCode + "1";
									}
									if(enemy2.isSelected()) {
										fullSkillCode = fullSkillCode + "2";
									}
									
									if(enemy3.isSelected()) {
										fullSkillCode = fullSkillCode + "3";
									}
									
									if(enemy4.isSelected()) {
										fullSkillCode = fullSkillCode + "4";
									}
									
							}
							
							if(isCritValidCheck.isSelected()) { 
								fullSkillCode = fullSkillCode + " .is_crit_valid True";
							}
							
							
							
							
							
							if(!effect1TextField.getText().toString().matches("") || !effect2TextField.getText().toString().matches("") || !effect3TextField.getText().toString().matches("") || !effect4TextField.getText().toString().matches("") || !effect5TextField.getText().toString().matches("")) {
								fullSkillCode = fullSkillCode + "  .effect";
								
								if(!effect1TextField.getText().toString().matches("")) {
									fullSkillCode = fullSkillCode + " \"" + effect1TextField.getText().toString() + "\"";
								}
	
								if(!effect2TextField.getText().toString().matches("")) {
									fullSkillCode = fullSkillCode + " \"" + effect2TextField.getText().toString() + "\"";
								}
								
								if(!effect3TextField.getText().toString().matches("")) {
									fullSkillCode = fullSkillCode + " \"" + effect3TextField.getText().toString() + "\"";
								}
								
								if(!effect4TextField.getText().toString().matches("")) {
									fullSkillCode = fullSkillCode + " \"" + effect4TextField.getText().toString() + "\"";
								}
								
								if(!effect5TextField.getText().toString().matches("")) {
									fullSkillCode = fullSkillCode + " \"" + effect5TextField.getText().toString() + "\"";
								}
							}
							
							if(generationCheck.isSelected()) {
								fullSkillCode = fullSkillCode + " .generation_guaranteed true";
							}
							
							if(!invalidingCheck.isSelected()) {
								fullSkillCode = fullSkillCode + " .is_stall_invalidating false";
							}
							
							int tempAttack = 0;
							int tempDamage = 0;
							int tempCrit = 0;
							int tempHealHighInt = 0;
							int tempHealLowInt = 0;
							
							int tempAttackPerLevel = 0;
							int tempDamagePerLevel = 0;
							int tempCritPerLevel = 0;
							
							int tempHealHighPerLevel = 0;
							int tempHealLowPerLevel = 0;
							
							String effect1String = effect1TextField.getText().toString();
							String effect2String = effect2TextField.getText().toString();
							String effect3String = effect3TextField.getText().toString();
							String effect4String = effect4TextField.getText().toString();
							String effect5String = effect5TextField.getText().toString();
							
							if(effect1String.contains(" 1")) {
								string1Boolean = true;
								effect1StringNoNum = effect1String.substring(0, effect1String.length()-2); 
							}
							if(effect2String.contains(" 1")) {
								string2Boolean = true;
								effect2StringNoNum = effect2String.substring(0, effect2String.length()-2);
							}
							if(effect3String.contains(" 1")) {
								string3Boolean = true;	
								effect3StringNoNum = effect3String.substring(0, effect3String.length()-2); 
							}
							if(effect4String.contains(" 1")) {
								string4Boolean = true;
								effect4StringNoNum = effect4String.substring(0, effect4String.length()-2); 
							}
							if(effect5String.contains(" 1")) {
								string5Boolean = true;
								effect5StringNoNum = effect5String.substring(0, effect5String.length()-2);
							}

							
							String tempNextLevelCode = fullSkillCode;
							tempNextLevelCode = tempNextLevelCode.replaceAll(" .generation_guaranteed true", "");
							
							if(damageTypeCombo.getValue().toString() == "Heal") {
								try {
									tempHealHighInt = Integer.valueOf(tempHealHigh);
									tempHealLowInt = Integer.valueOf(tempHealLow);
									
									tempHealHighPerLevel = Integer.valueOf(healHighPerLevelTextField.getText().toString());
									tempHealLowPerLevel = Integer.valueOf(healLowPerLevelTextField.getText().toString());
									
									int tempTotalHealHigh = tempHealHighInt;
									int tempTotalHealLow = tempHealLowInt;
									
									for(int i = 0; i < 4; i++) {
										int tempLevelUp = i + 1;
										tempNextLevelCode = tempNextLevelCode.replaceAll(".level " + i, ".level " + tempLevelUp);
										
										tempTotalHealHigh = tempTotalHealHigh + tempHealHighPerLevel;
										tempTotalHealLow = tempTotalHealLow + tempHealLowPerLevel;
										
										int oldNum1 = tempTotalHealLow - tempHealLowPerLevel;
										int oldNum = tempTotalHealHigh - tempHealHighPerLevel;
										
										tempNextLevelCode = tempNextLevelCode.replaceAll(" .heal " + oldNum1 + " ", " .heal " + tempTotalHealLow + " ");
										tempNextLevelCode = tempNextLevelCode.replaceAll(" .heal " + tempTotalHealLow + " " + oldNum + " ", " .heal " + tempTotalHealLow + " " + tempTotalHealHigh + " ");
									
										tempNextLevelCode = increaseEffectLevel(string1Boolean,string2Boolean,string3Boolean,string4Boolean,string5Boolean,
			   									effect1StringNoNum, effect2StringNoNum, effect3StringNoNum, effect4StringNoNum, effect5StringNoNum, tempNextLevelCode, incrEffectsCheck);
										
										
										fullSkillCode = fullSkillCode + '\n' + tempNextLevelCode;
									}
									
									////////Copy Method
									 StringSelection stringSelection = new StringSelection(fullSkillCode);
						   			    Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
						   			    clpbrd.setContents(stringSelection, null);
									/////////////
						   					
						   			    	displayStatus("", 1200, true, successLabel, errorLabel);
									
								} catch(Exception ex) {
									
									displayStatus("Number Parse Error.", 5000, false, successLabel, errorLabel);
								}
								
							} else {
								try {
									tempAttack = Integer.valueOf(tempAttackTextField);
									tempDamage = Integer.valueOf(tempDamageTextField);
									tempCrit = Integer.valueOf(tempCritTextField);
									
									tempAttackPerLevel = Integer.valueOf(attackPerLevelTextField.getText().toString());
									tempDamagePerLevel = Integer.valueOf(damagePerLevelTextField.getText().toString());
									tempCritPerLevel = Integer.valueOf(critPerLevelTextField.getText().toString());
									
									int tempTotalAttack = tempAttack;
									int tempTotalDamage = tempDamage;
									int tempTotalCrit = tempCrit;
									
								
									
									for(int i = 0; i < 4; i++) {
										int tempLevelUp = i + 1;
										
										tempNextLevelCode = tempNextLevelCode.replaceAll(".level " + i, ".level " + tempLevelUp);
										
										tempTotalAttack = tempTotalAttack + tempAttackPerLevel;
										tempTotalDamage = tempTotalDamage + tempDamagePerLevel;
										tempTotalCrit = tempTotalCrit + tempCritPerLevel;
										
										tempNextLevelCode = tempNextLevelCode.replaceAll(" .atk .*% .d", " .atk " + tempTotalAttack + "% .d");
										tempNextLevelCode = tempNextLevelCode.replaceAll(" .dmg .*% .c", " .dmg " + tempTotalDamage + "% .c");
										tempNextLevelCode = tempNextLevelCode.replaceAll(" .crit .*% ", " .crit " + tempTotalCrit + "% ");
										
										tempNextLevelCode = increaseEffectLevel(string1Boolean,string2Boolean,string3Boolean,string4Boolean,string5Boolean,
			   									effect1StringNoNum, effect2StringNoNum, effect3StringNoNum, effect4StringNoNum, effect5StringNoNum, tempNextLevelCode, incrEffectsCheck);
									
										
										fullSkillCode = fullSkillCode + '\n' + tempNextLevelCode;
									}
									
									
									////////Copy Method
									 StringSelection stringSelection = new StringSelection(fullSkillCode);
						   			    Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
						   			    clpbrd.setContents(stringSelection, null);
									/////////////
						   			    
						   			    	displayStatus("", 1200, true, successLabel, errorLabel);
									
								} catch(Exception ex) {
									displayStatus("Number Parse Error.", 5000, false, successLabel, errorLabel);
								}
							}

							
						}
						
					}
					
					
				} else {
					//name blank
	   				displayStatus("Skill Name", 5000, false, successLabel, errorLabel);
				}
					
		});
		
		damageTypeCombo.setOnAction(e -> {
			if((damageTypeCombo.getValue().toString() == "Melee") || (damageTypeCombo.getValue().toString() == "Range")) {
				isCritValidCheck.setVisible(true);
				dpsPerLevelGridPane.setVisible(true);
				damageGridPane.setVisible(true);
				healGridPane.setVisible(false);
				healPerLevelGridPane.setVisible(false);
				//
			} else {
				//Heal is selected
				dpsPerLevelGridPane.setVisible(false);
				isCritValidCheck.setVisible(false);
				damageGridPane.setVisible(false);
				healGridPane.setVisible(true);
				healPerLevelGridPane.setVisible(true);
			}
		});
		
		targetTypeCombo.setOnAction(e -> {
			if(targetTypeCombo.getValue().toString() == "Self") {
				multipleTargetsCheck.setVisible(false);
				enemyHBox.setVisible(false);
			} else {
				enemyHBox.setVisible(true);
				multipleTargetsCheck.setVisible(true);
			}
		});
		
		
		///////////////Set Coordinates	
		skillNameLabel.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 18.0));
		skillNameLabel.setLayoutX(144.0);
		skillNameLabel.setLayoutY(27.0); 
		skillNameLabel.setTextFill(Color.web("#ff8c00ff"));
		
		skillNameTextField.setLayoutX(250.0);
		skillNameTextField.setLayoutY(27.0);
		
		damageTypeCombo.setLayoutX(107.0);
		damageTypeCombo.setLayoutY(106.0);
		
		attackLabel.setTextFill(Color.web("#ff8c00ff"));
		attackLabel.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 12.0));
		damageLabel.setTextFill(Color.web("#ff8c00ff"));
		damageLabel.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 12.0));
		critLabel.setTextFill(Color.web("#ff8c00ff"));
		critLabel.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 12.0));

		dpsPerLevelGridPane.setLayoutX(350);
		dpsPerLevelGridPane.setLayoutY(80.0);
		dpsPerLevelGridPane.setVgap(3);
		attackPerLevelTextField.setPrefWidth(42);
		damagePerLevelTextField.setPrefWidth(42);
		critPerLevelTextField.setPrefWidth(42);
		
		damageGridPane.setLayoutX(218.0);
		damageGridPane.setLayoutY(80.0);
		damageGridPane.setVgap(3);
		attackTextField.setPrefWidth(42);
		damageTextField.setPrefWidth(42);
		critTextField.setPrefWidth(42);
		
		healHighLabel.setTextFill(Color.web("#ff8c00ff"));
		healHighLabel.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 12.0));
		healLowLabel.setTextFill(Color.web("#ff8c00ff"));
		healLowLabel.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 12.0));
		
		healGridPane.setLayoutX(218.0); 
		healGridPane.setLayoutY(90.0);
		healGridPane.setVgap(3);
		
		healHighTextField.setPrefWidth(42);
		healLowTextField.setPrefWidth(42);
		
		healPerLevelGridPane.setVisible(false);
		healPerLevelGridPane.setLayoutX(348.0);
		healPerLevelGridPane.setLayoutY(91.0);
		healPerLevelGridPane.setVgap(3);
		healHighPerLevelTextField.setPrefWidth(42);
		healLowPerLevelTextField.setPrefWidth(42);

		isCritValidCheck.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 12.0));
		isCritValidCheck.setLayoutX(440.0);
		isCritValidCheck.setLayoutY(100.0); 
		isCritValidCheck.setTextFill(Color.web("#ff8c00ff"));
		
		playerHBox.setLayoutX(96.0);
		playerHBox.setLayoutY(242.0); 
		
		enemyHBox.setLayoutX(413.0);
		enemyHBox.setLayoutY(251.0);
		
		effectsVBox.setLayoutX(222.0);
		effectsVBox.setLayoutY(278.0);
		effectsVBox.setSpacing(3);
		
		playerLabel.setLayoutX(80.0);
		playerLabel.setLayoutY(213.0);
		playerLabel.setTextFill(Color.web("#ff8c00ff"));
		playerLabel.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 14.0));
		
		targetLabel.setTextFill(Color.web("#ff8c00ff"));
		targetLabel.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 14.0));
		targetHBox.setLayoutX(375.0);
		targetHBox.setLayoutY(213.0);

		generationCheck.setLayoutX(220.0);
		generationCheck.setLayoutY(537.0);
		generationCheck.setTextFill(Color.web("#ff8c00ff"));
		generationCheck.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 12.0));

		invalidingCheck.setLayoutX(220.0);
		invalidingCheck.setLayoutY(440.0);
		invalidingCheck.setTextFill(Color.web("#ff8c00ff"));
		invalidingCheck.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 12.0));
		
		addEffectsLabel.setLayoutX(123.0);
		addEffectsLabel.setLayoutY(329.0);
		addEffectsLabel.setTextFill(Color.web("#ff8c00ff"));
		addEffectsLabel.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 14.0));
		
		damageTypeLabel.setLayoutX(90.0);
		damageTypeLabel.setLayoutY(79.0);
		damageTypeLabel.setTextFill(Color.web("#ff8c00ff"));
		damageTypeLabel.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 14.0));
		
		multipleTargetsCheck.setLayoutX(442.0);
		multipleTargetsCheck.setLayoutY(182.0);
		multipleTargetsCheck.setTextFill(Color.web("#ff8c00ff"));
		multipleTargetsCheck.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 14.0));
		
		helpButton.setLayoutX(478.0);
		helpButton.setLayoutY(21.0);
		
		copyButton.setLayoutX(219.0);
		copyButton.setLayoutY(575.0);
		
		moveForwardTextField.setLayoutX(260.0);
		moveForwardTextField.setLayoutY(466.0);
		moveForwardTextField.setPrefWidth(35);
		
		moveBackwardTextField.setLayoutX(220.0);
		moveBackwardTextField.setLayoutY(466.0);
		moveBackwardTextField.setPrefWidth(35);
		
		skillBattleLimitTextField.setLayoutX(220.0);
		skillBattleLimitTextField.setLayoutY(500.0);
		skillBattleLimitTextField.setPrefWidth(35);
		
		skillBattleLimitLabel.setLayoutX(55.0);
		skillBattleLimitLabel.setLayoutY(502.0);
		skillBattleLimitLabel.setTextFill(Color.web("#ff8c00ff"));
		skillBattleLimitLabel.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 14.0));
		
		moveBackwardLabel.setLayoutX(115.0);
		moveBackwardLabel.setLayoutY(473.0);
		moveBackwardLabel.setTextFill(Color.web("#ff8c00ff"));
		moveBackwardLabel.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 12.0));
		
		moveForwardLabel.setLayoutX(301.0);
		moveForwardLabel.setLayoutY(472.0);
		moveForwardLabel.setTextFill(Color.web("#ff8c00ff"));
		moveForwardLabel.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 12.0));
		
		successLabel.setLayoutX(341.0);
		successLabel.setLayoutY(579.0);
		successLabel.setTextFill(Color.LIMEGREEN);
		successLabel.setFont(Font.font("Lucida Bright", FontWeight.BOLD,FontPosture.REGULAR, 14.0));
		successLabel.setVisible(false);
		
		errorLabel.setLayoutX(341.0);
		errorLabel.setLayoutY(579.0);
		errorLabel.setTextFill(Color.YELLOW);
		errorLabel.setFont(Font.font("Lucida Bright", FontWeight.BOLD,FontPosture.REGULAR, 14.0));
		errorLabel.setVisible(false);
		
		perLevelLabel.setLayoutX(345.0);
		perLevelLabel.setLayoutY(167.0);
		perLevelLabel.setTextFill(Color.web("#ff8c00ff"));
		perLevelLabel.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 12.0));
		
		incrEffectsCheck.setTextFill(Color.web("#ff8c00ff"));
		incrEffectsCheck.setFont(Font.font("Lucida Bright", FontWeight.NORMAL,FontPosture.REGULAR, 12.0));
		incrEffectsCheck.setLayoutX(396.0);
		incrEffectsCheck.setLayoutY(326.0);

		//textDebugger(skillNameLabel, "skillNameLabel", root, scene, primaryStage);


		///////////////Add to scene
		
		damageGridPane.add(attackLabel,0,0);
		damageGridPane.add(attackTextField,1,0);
		damageGridPane.add(damageLabel,0,1);
		damageGridPane.add(damageTextField,1,1);
		damageGridPane.add(critLabel,0,2);
		damageGridPane.add(critTextField,1,2);
		
		healPerLevelGridPane.add(healHighPerLevelTextField, 0, 0);
		healPerLevelGridPane.add(healLowPerLevelTextField, 0, 1);
		
		healGridPane.add(healHighLabel, 0, 0);
		healGridPane.add(healHighTextField, 1, 0);
		healGridPane.add(healLowLabel, 0, 1);
		healGridPane.add(healLowTextField, 1, 1);
		
		dpsPerLevelGridPane.add(attackPerLevelTextField, 0, 0);
		dpsPerLevelGridPane.add(damagePerLevelTextField, 0, 1);
		dpsPerLevelGridPane.add(critPerLevelTextField, 0, 2);
		
		playerHBox.getChildren().add(player4);
		playerHBox.getChildren().add(player3);
		playerHBox.getChildren().add(player2);
		playerHBox.getChildren().add(player1);
		
		enemyHBox.getChildren().add(enemy1);
		enemyHBox.getChildren().add(enemy2);
		enemyHBox.getChildren().add(enemy3);
		enemyHBox.getChildren().add(enemy4);
		
		effectsVBox.getChildren().add(effect1TextField);
		effectsVBox.getChildren().add(effect2TextField);
		effectsVBox.getChildren().add(effect3TextField);
		effectsVBox.getChildren().add(effect4TextField);
		effectsVBox.getChildren().add(effect5TextField);
		
		targetHBox.getChildren().add(targetLabel);
		targetHBox.getChildren().add(targetTypeCombo);
		
		root.getChildren().add(incrEffectsCheck);
		root.getChildren().add(healPerLevelGridPane);
		root.getChildren().add(perLevelLabel);
		root.getChildren().add(dpsPerLevelGridPane);
		root.getChildren().add(errorLabel);
		root.getChildren().add(successLabel);
		root.getChildren().add(moveBackwardLabel);
		root.getChildren().add(moveForwardLabel);
		root.getChildren().add(generationCheck);
		root.getChildren().add(skillBattleLimitLabel);
		root.getChildren().add(skillBattleLimitTextField);
		root.getChildren().add(moveForwardTextField);
		root.getChildren().add(moveBackwardTextField);
		root.getChildren().add(copyButton);
		root.getChildren().add(helpButton);
		root.getChildren().add(multipleTargetsCheck);
		root.getChildren().add(damageTypeLabel);
		root.getChildren().add(addEffectsLabel);
		root.getChildren().add(invalidingCheck);
		root.getChildren().add(playerLabel);
		root.getChildren().add(targetHBox);
		root.getChildren().add(effectsVBox);
		root.getChildren().add(enemyHBox);
		root.getChildren().add(playerHBox);
		root.getChildren().add(healGridPane);
		root.getChildren().add(damageGridPane);
		root.getChildren().add(isCritValidCheck);
		root.getChildren().add(damageTypeCombo);
		root.getChildren().add(skillNameLabel);
		root.getChildren().add(skillNameTextField);
		primaryStage.setScene(scene);
		primaryStage.show();
		skillNameTextField.requestFocus();
		//////////////////////////////////////////////
	}
	
	   public static void infoButtonProperties(Button helpButton) {
		   Alert info = new Alert(AlertType.INFORMATION);
	   		info.getDialogPane().setMinWidth(500);
	   		info.getDialogPane().setMinHeight(100);
	   		info.setTitle("Darkest Dungeon Skill Creator Help");
	   		info.setHeaderText("                                           Information");
		    String sInfo = "This is a tool used to easily create new skills from scratch for modding Darkest Dungeon." + '\n' + '\n' + "              After creating a skill, it's copied for you to paste in your hero's info file." + '\n' +'\n' + "                                             Created by Robert Robinson 2018" + '\n' + "                                             Certified Java Software Developer";
		    info.setContentText(sInfo);
		    
		    helpButton.setOnAction(e -> {
	   			info.show();
	   		});
	   }
	   
	   public static String increaseEffectLevel(Boolean string1Boolean, Boolean string2Boolean, Boolean string3Boolean, Boolean string4Boolean, Boolean string5Boolean,
			   									String effect1StringNoNum, String effect2StringNoNum, String effect3StringNoNum, String effect4StringNoNum, String effect5StringNoNum,
			   									String tempNextLevelCode, CheckBox incrEffectsCheck) {
		   
		   if(incrEffectsCheck.isSelected()) {
			   
					   if(string1Boolean == true) {
								tempNextLevelCode = tempNextLevelCode.replaceAll(effect1StringNoNum + " 4", effect1StringNoNum + " 5");
								tempNextLevelCode = tempNextLevelCode.replaceAll(effect1StringNoNum + " 3", effect1StringNoNum + " 4");
								tempNextLevelCode = tempNextLevelCode.replaceAll(effect1StringNoNum + " 2", effect1StringNoNum + " 3");
								tempNextLevelCode = tempNextLevelCode.replaceAll(effect1StringNoNum + " 1", effect1StringNoNum + " 2");
						} 
					   
					   if(string2Boolean == true) {
						    tempNextLevelCode = tempNextLevelCode.replaceAll(effect2StringNoNum + " 4", effect2StringNoNum + " 5");
							tempNextLevelCode = tempNextLevelCode.replaceAll(effect2StringNoNum + " 3", effect2StringNoNum + " 4");
							tempNextLevelCode = tempNextLevelCode.replaceAll(effect2StringNoNum + " 2", effect2StringNoNum + " 3");
							tempNextLevelCode = tempNextLevelCode.replaceAll(effect2StringNoNum + " 1", effect2StringNoNum + " 2");
					   }
					   
					   if(string3Boolean == true) {
						    tempNextLevelCode = tempNextLevelCode.replaceAll(effect3StringNoNum + " 4", effect3StringNoNum + " 5");
							tempNextLevelCode = tempNextLevelCode.replaceAll(effect3StringNoNum + " 3", effect3StringNoNum + " 4");
							tempNextLevelCode = tempNextLevelCode.replaceAll(effect3StringNoNum + " 2", effect3StringNoNum + " 3");
							tempNextLevelCode = tempNextLevelCode.replaceAll(effect3StringNoNum + " 1", effect3StringNoNum + " 2");
					   }
					   
					   if(string4Boolean == true) {
						    tempNextLevelCode = tempNextLevelCode.replaceAll(effect4StringNoNum + " 4", effect4StringNoNum + " 5");
							tempNextLevelCode = tempNextLevelCode.replaceAll(effect4StringNoNum + " 3", effect4StringNoNum + " 4");
							tempNextLevelCode = tempNextLevelCode.replaceAll(effect4StringNoNum + " 2", effect4StringNoNum + " 3");
							tempNextLevelCode = tempNextLevelCode.replaceAll(effect4StringNoNum + " 1", effect4StringNoNum + " 2");
					   }
					   
					   if(string5Boolean == true) {
						    tempNextLevelCode = tempNextLevelCode.replaceAll(effect5StringNoNum + " 4", effect5StringNoNum + " 5");
							tempNextLevelCode = tempNextLevelCode.replaceAll(effect5StringNoNum + " 3", effect5StringNoNum + " 4");
							tempNextLevelCode = tempNextLevelCode.replaceAll(effect5StringNoNum + " 2", effect5StringNoNum + " 3");
							tempNextLevelCode = tempNextLevelCode.replaceAll(effect5StringNoNum + " 1", effect5StringNoNum + " 2");
					   }
		   }
		   return tempNextLevelCode;
		   
	   }
	   
	   public static void displayStatus(String errorStatus, int displayTime, Boolean isSuccess, Label successLabel, Label errorLabel) {
		   
		   if(isSuccess == false) {
			   
			   	errorLabel.setVisible(false);
				successLabel.setVisible(false);
			   
					errorLabel.setText("Error: " + '\n' + errorStatus);
					
					errorLabel.setVisible(true);
						Timeline timeline = new Timeline(new KeyFrame(
								Duration.millis(displayTime),
								ae -> { 
									errorLabel.setVisible(false);
								}));
							timeline.play();
		   } else {
			   
				errorLabel.setVisible(false);
				successLabel.setVisible(true);
			   
			   Timeline timeline = new Timeline(new KeyFrame(
  						Duration.millis(displayTime),
  						ae -> { 
  							successLabel.setVisible(false);
  						}));
  					timeline.play();
		   }
	   }
	
	public static void textDebugger(Label textLabel, String copyClipString, Group root, Scene scene, Stage primaryStage) { int sceneHeight = (int) scene.getHeight(); int sceneWidth = (int) scene.getWidth(); primaryStage.setHeight(sceneHeight + 160); Line displayLine; if(sceneWidth < 447) { double difference = 447 - sceneWidth; primaryStage.setWidth(sceneWidth + difference + 40); displayLine = new Line(0,sceneHeight,sceneWidth + difference + 40,sceneHeight); } else { displayLine = new Line(0,sceneHeight,sceneWidth,sceneHeight); } GridPane textProperties = new GridPane(); TextField textSize = new TextField(); TextField copyTextField = new TextField(); copyTextField.setText(copyClipString); copyTextField.setPrefWidth(45); textSize.setPrefWidth(45); ComboBox<String> comboFont = new ComboBox<String>(); ComboBox<String> comboColor = new ComboBox<String>(); CheckBox boldCheckbox = new CheckBox("Bold"); CheckBox italicCheckbox = new CheckBox("Italic"); CheckBox invertTextProp = new CheckBox("GUI"); CheckBox backgroundCheckbox = new CheckBox("Scene " + '\n'+ "Color"); Button copyToClipboard = new Button("Copy Clipboard"); Button helpButton = new Button("?"); comboFont.getItems().addAll( "Arial", "Arial Black", "Calibri", "Calibri Light", "Cambria", "Cambria Math", "Candara", "Comic Sans MS", "Consolas", "Constantia", "Corbel", "Courier New", "Ebrima", "Franklin Gothic Medium", "Gabriola", "Gadugi", "Georgia", "HoloLens MDL2 Assets", "Impact", "Javanese Text", "Leelawadee UI", "Leelawadee UI Semilight", "Lucida Bright", "Lucida Console", "Lucida Sans", "Lucida Sans Typewriter", "Lucida Sans Unicode", "MS Gothic", "MS PGothic", "MS UI Gothic", "MV Boli", "Malgun Gothic", "Malgun Gothic Semilight", "Marlett", "Microsoft Himalaya", "Microsoft JhengHei", "Microsoft JhengHei Light", "Microsoft JhengHei UI", "Microsoft JhengHei UI Light", "Microsoft New Tai Lue", "Microsoft PhagsPa", "Microsoft Sans Serif", "Microsoft Tai Le", "Microsoft YaHei", "Microsoft YaHei Light", "Microsoft YaHei UI", "Microsoft YaHei UI Light", "Microsoft Yi Baiti", "MingLiU-ExtB", "MingLiU_HKSCS-ExtB", "Mongolian Baiti", "Monospaced", "Myanmar Text", "NSimSun", "Nirmala UI", "Nirmala UI Semilight", "PMingLiU-ExtB", "Palatino Linotype", "SansSerif", "Segoe MDL2 Assets", "Segoe Print", "Segoe Script", "Segoe UI", "Segoe UI Black", "Segoe UI Emoji", "Segoe UI Historic", "Segoe UI Light", "Segoe UI Semibold", "Segoe UI Semilight", "Segoe UI Symbol", "Serif", "SimSun", "SimSun-ExtB", "Sitka Banner", "Sitka Display", "Sitka Heading", "Sitka Small", "Sitka Subheading", "Sitka Text", "Sylfaen", "Symbol", "System", "Tahoma", "Times New Roman", "Trebuchet MS", "Unispace", "Verdana", "Webdings", "Wingdings", "Yu Gothic", "Yu Gothic Light", "Yu Gothic Medium", "Yu Gothic UI", "Yu Gothic UI Light", "Yu Gothic UI Semibold", "Yu Gothic UI Semilight"); comboColor.getItems().addAll( "ALICEBLUE", "ANTIQUEWHITE", "AQUA", "AQUAMARINE", "AZURE", "BEIGE", "BISQUE", "BLACK", "BLANCHEDALMOND", "BLUE", "BLUEVIOLET", "BROWN", "BURLYWOOD", "CADETBLUE", "CHARTREUSE", "CHOCOLATE", "CORAL", "CORNFLOWERBLUE", "CORNSILK", "CRIMSON", "CYAN", "DARKBLUE", "DARKCYAN", "DARKGOLDENROD", "DARKGREEN", "DARKGREY", "DARKKHAKI", "DARKMAGENTA", "DARKOLIVEGREEN", "DARKORANGE", "DARKORCHID", "DARKRED", "DARKSALMON", "DARKSEAGREEN", "DARKSLATEBLUE", "DARKSLATEGREY", "DARKTURQUOISE", "DARKVIOLET", "DEEPPINK", "DEEPSKYBLUE", "DIMGREY", "DODGERBLUE", "FIREBRICK", "FLORALWHITE", "FORESTGREEN", "FUCHSIA", "GAINSBORO", "GHOSTWHITE", "GOLD", "GOLDENROD", "GREEN", "GREENYELLOW", "GREY", "HONEYDEW", "HOTPINK", "INDIANRED", "INDIGO", "IVORY", "KHAKI", "LAVENDER", "LAVENDERBLUSH", "LAWNGREEN", "LEMONCHIFFON", "LIGHTBLUE", "LIGHTCORAL", "LIGHTCYAN", "LIGHTGOLDENRODYELLOW", "LIGHTGREEN", "LIGHTGREY", "LIGHTPINK", "LIGHTSALMON", "LIGHTSEAGREEN", "LIGHTSKYBLUE", "LIGHTSLATEGREY", "LIGHTSTEELBLUE", "LIGHTYELLOW", "LIME", "LIMEGREEN", "LINEN", "MAGENTA", "MAROON", "MEDIUMAQUAMARINE", "MEDIUMBLUE", "MEDIUMORCHID", "MEDIUMPURPLE", "MEDIUMSEAGREEN", "MEDIUMSLATEBLUE", "MEDIUMSPRINGGREEN", "MEDIUMTURQUOISE", "MEDIUMVIOLETRED", "MIDNIGHTBLUE", "MINTCREAM", "MISTYROSE", "MOCCASIN", "NAVAJOWHITE", "NAVY", "OLDLACE", "OLIVE", "OLIVEDRAB", "ORANGE", "ORANGERED", "ORCHID", "PALEGOLDENROD", "PALEGREEN", "PALETURQUOISE", "PALEVIOLETRED", "PAPAYAWHIP", "PEACHPUFF", "PERU", "PLUM", "POWDERBLUE", "PURPLE", "RED", "ROSYBROWN", "ROYALBLUE", "SADDLEBROWN", "SALMON", "SANDYBROWN", "SEAGREEN", "SEASHELL", "SIENNA", "SILVER", "SKYBLUE", "SLATEBLUE", "SLATEGREY", "SNOW", "SPRINGGREEN", "STEELBLUE", "TAN", "TEAL", "THISTLE", "TOMATO", "TRANSPARENT", "TURQUOISE", "VIOLET", "WHEAT", "WHITESMOKE", "YELLOW", "YELLOWGREEN"); comboFont.setPrefWidth(150); comboColor.setPrefWidth(150); Label copyInfoTag = new Label("Enter Label Name:"); Label sceneXLabel = new Label(""); Label copyConfirmed = new Label(" Copied."); Label copyError = new Label(" Error no Label."); copyConfirmed.setVisible(false); copyError.setVisible(false); invertTextProp.setTextFill(Color.ANTIQUEWHITE); Label spacefill1 = new Label(" "); Label fillerProp2 = new Label(" "); copyError.setTextFill(Color.ANTIQUEWHITE); copyInfoTag.setTextFill(Color.ANTIQUEWHITE); sceneXLabel.setTextFill(Color.ANTIQUEWHITE); copyConfirmed.setTextFill(Color.ANTIQUEWHITE); backgroundCheckbox.setTextFill(Color.ANTIQUEWHITE); boldCheckbox.setTextFill(Color.ANTIQUEWHITE); italicCheckbox.setTextFill(Color.ANTIQUEWHITE); textSize.setText("16"); sceneXLabel.setText(""); textProperties.setLayoutX(24.0); textProperties.setLayoutY(sceneHeight + 15); textProperties.add(comboFont, 0, 0); textProperties.add(textSize, 1, 0); textProperties.add(sceneXLabel, 0, 2); textProperties.add(backgroundCheckbox, 1, 2); textProperties.add(comboColor, 0, 1); textProperties.add(spacefill1, 3, 1); textProperties.add(boldCheckbox, 2, 1); textProperties.add(fillerProp2, 5, 1); textProperties.add(helpButton, 6, 1); textProperties.add(invertTextProp, 6, 2); textProperties.add(italicCheckbox, 2, 2); textProperties.add(copyInfoTag, 4, 0); textProperties.add(copyTextField, 4, 1); textProperties.add(copyToClipboard, 4, 2); textProperties.add(copyConfirmed, 4, 3); textProperties.add(copyError, 4, 3); root.getChildren().add(displayLine); root.getChildren().add(textProperties); Alert alert = new Alert(AlertType.INFORMATION); alert.getDialogPane().setMinWidth(550); alert.setTitle("Debug Information"); alert.setHeaderText("Information"); String sAlert ="This is a method used to style, place Labels and also get coordinates to place other widgets easily." + '\n' + '\n' + "Controls:" + '\n' + '\n' + "Mouse Left Click = Place the parameter Label at X/Y mouse coordinates." + '\n' + "Mouse Right Click = Copy current mouse coordinates to clipboard" + '\n' + "Ctrl + M = Place debugging GUI where mouse coordinates are." + '\n' + "Ctrl + W = Copy current Window Width/Height. (Good for resizing windows)" +'\n' + '\n' + "Copy to Clipboard once your Label is perfectly how you want it." + '\n' + "Change the parameters to apply changes to other desired Labels." + '\n' +'\n' + "Created by Robert Robinson 2018"; alert.setContentText(sAlert); String levelsCheck = "1"; try { levelsCheck = textLabel.getParent().toString(); } catch (Exception ex) { levelsCheck = "0"; } if(!levelsCheck.contains("Group") && !root.getChildren().contains(textLabel) && levelsCheck != "0"){ double getterX1 = textLabel.getParent().getLayoutX(); double getterY1 = textLabel.getParent().getLayoutY(); root.getChildren().add(textLabel); textLabel.setLayoutX(getterX1); textLabel.setLayoutY(getterY1); } else { } sceneXLabel.setText(" "+ textLabel.getLayoutX() + " " + textLabel.getLayoutY()); helpButton.setOnMouseClicked(e -> { alert.show(); }); invertTextProp.setOnAction(e -> { if(invertTextProp.isSelected()) { copyError.setTextFill(Color.BLACK); copyInfoTag.setTextFill(Color.BLACK); sceneXLabel.setTextFill(Color.BLACK); copyConfirmed.setTextFill(Color.BLACK); backgroundCheckbox.setTextFill(Color.BLACK); boldCheckbox.setTextFill(Color.BLACK); italicCheckbox.setTextFill(Color.BLACK); invertTextProp.setTextFill(Color.BLACK); } else { copyError.setTextFill(Color.ANTIQUEWHITE); copyInfoTag.setTextFill(Color.ANTIQUEWHITE); sceneXLabel.setTextFill(Color.ANTIQUEWHITE); copyConfirmed.setTextFill(Color.ANTIQUEWHITE); backgroundCheckbox.setTextFill(Color.ANTIQUEWHITE); boldCheckbox.setTextFill(Color.ANTIQUEWHITE); italicCheckbox.setTextFill(Color.ANTIQUEWHITE); invertTextProp.setTextFill(Color.ANTIQUEWHITE); } }); copyToClipboard.setOnAction(e -> { if(copyTextField.getText().length() > 0) { copyConfirmed.setVisible(true); String labelVarCopy = copyTextField.getText(); String mySceneColor = scene.getFill().toString(); String myColor = textLabel.getTextFill().toString(); char[] ch1=mySceneColor.toCharArray(); char[] ch=myColor.toCharArray(); String newColorString = ""; String newSceneColorString = ""; for(int i = 2; i < ch1.length; i++) { newSceneColorString = newSceneColorString + ch1[i]; } for(int i = 2; i < ch.length; i++) { newColorString = newColorString + ch[i]; } String clipboardCopy; if(textLabel.getFont().getStyle().contains("Bold")) { clipboardCopy = labelVarCopy + ".setFont(Font.font(\"" + textLabel.getFont().getFamily() + "\", FontWeight.BOLD,FontPosture."; } else { clipboardCopy = labelVarCopy + ".setFont(Font.font(\"" + textLabel.getFont().getFamily() + "\", FontWeight.NORMAL,FontPosture."; } if(textLabel.getFont().getStyle().contains("Italic")) { clipboardCopy = clipboardCopy + "ITALIC, " + textLabel.getFont().getSize() + "));"+ '\n' + labelVarCopy + ".setLayoutX(" + textLabel.getLayoutX() + "); //If coordinates aren't correct it means you have to set them to the parent." + '\n' + labelVarCopy + ".setLayoutY(" + textLabel.getLayoutY() + "); //If coordinates aren't correct it means you have to set them to the parent." + '\n' + labelVarCopy + ".setTextFill(Color.web(\"#" + newColorString + "\"));"; } else { clipboardCopy = clipboardCopy + "REGULAR, " + textLabel.getFont().getSize() + "));"+ '\n' + labelVarCopy + ".setLayoutX(" + textLabel.getLayoutX() + "); //If coordinates aren't correct it means you have to set them to the parent." + '\n' + labelVarCopy + ".setLayoutY(" + textLabel.getLayoutY() + "); //If coordinates aren't correct it means you have to set them to the parent." + '\n' + labelVarCopy + ".setTextFill(Color.web(\"#" + newColorString + "\"));"; } clipboardCopy = clipboardCopy + '\n' + "//Scene Color: Color.web(\"#" + newSceneColorString + "\")"; StringSelection stringSelection = new StringSelection(clipboardCopy); Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard(); clpbrd.setContents(stringSelection, null); Timeline timeline = new Timeline(new KeyFrame( Duration.millis(2500), ae -> { copyConfirmed.setVisible(false); })); timeline.play(); } else { copyError.setVisible(true); Timeline timeline = new Timeline(new KeyFrame( Duration.millis(2500), ae -> { copyError.setVisible(false); })); timeline.play(); } }); italicCheckbox.setOnAction(e -> { int convertedFontSize = 16; root.getChildren().remove(textLabel); try { convertedFontSize = Integer.parseInt(textSize.getText());} catch (Exception ex) { } if(italicCheckbox.isSelected()) { if(boldCheckbox.isSelected()) { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.BOLD,FontPosture.ITALIC, convertedFontSize)); } else { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.NORMAL,FontPosture.ITALIC, convertedFontSize)); } } else { if(boldCheckbox.isSelected()) { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.BOLD,FontPosture.REGULAR, convertedFontSize)); } else { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.NORMAL,FontPosture.REGULAR, convertedFontSize)); } } root.getChildren().add(textLabel); }); boldCheckbox.setOnAction(e -> { int convertedFontSize = 16; root.getChildren().remove(textLabel); try { convertedFontSize = Integer.parseInt(textSize.getText());} catch (Exception ex) { } if(boldCheckbox.isSelected()) { if(italicCheckbox.isSelected()) { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.BOLD, FontPosture.ITALIC, convertedFontSize)); } else { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.BOLD, FontPosture.REGULAR, convertedFontSize)); } } else { if(italicCheckbox.isSelected()) { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.NORMAL,FontPosture.ITALIC, convertedFontSize)); } else { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.NORMAL,FontPosture.REGULAR, convertedFontSize)); } } root.getChildren().add(textLabel); }); scene.setOnMouseMoved(ex -> { scene.setOnKeyPressed(e -> { if(e.isControlDown()) { if(e.getCode().equals(KeyCode.W)) { System.out.println(scene.getWidth()+ ", " + (scene.getHeight())); String clipboardCopy2 = scene.getWidth() + ", " + (scene.getHeight()); StringSelection stringSelection2 = new StringSelection(clipboardCopy2); Clipboard clpbrd2 = Toolkit.getDefaultToolkit().getSystemClipboard(); clpbrd2.setContents(stringSelection2, null); } else if (e.getCode().equals(KeyCode.M)) { root.getChildren().remove(textProperties); textProperties.setLayoutX(ex.getSceneX()); textProperties.setLayoutY(ex.getSceneY()); root.getChildren().add(textProperties); } } }); }); comboColor.setOnAction(e -> { if(backgroundCheckbox.isSelected()) { if(comboColor.getSelectionModel().getSelectedItem() == "ALICEBLUE") { scene.setFill(Color.ALICEBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "ANTIQUEWHITE") { scene.setFill(Color.ANTIQUEWHITE); } else if (comboColor.getSelectionModel().getSelectedItem() == "AQUA") { scene.setFill(Color.AQUA); } else if (comboColor.getSelectionModel().getSelectedItem() == "AQUAMARINE") { scene.setFill(Color.AQUAMARINE); } else if (comboColor.getSelectionModel().getSelectedItem() == "AZURE") { scene.setFill(Color.AZURE); } else if (comboColor.getSelectionModel().getSelectedItem() == "BEIGE") { scene.setFill(Color.BEIGE); } else if (comboColor.getSelectionModel().getSelectedItem() == "BISQUE") { scene.setFill(Color.BISQUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "BLACK") { scene.setFill(Color.BLACK); } else if (comboColor.getSelectionModel().getSelectedItem() == "BLANCHEDALMOND") { scene.setFill(Color.BLANCHEDALMOND); } else if (comboColor.getSelectionModel().getSelectedItem() == "BLUE") { scene.setFill(Color.BLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "BLUEVIOLET") { scene.setFill(Color.BLUEVIOLET); } else if (comboColor.getSelectionModel().getSelectedItem() == "BROWN") { scene.setFill(Color.BROWN); } else if (comboColor.getSelectionModel().getSelectedItem() == "BURLYWOOD") { scene.setFill(Color.BURLYWOOD); } else if (comboColor.getSelectionModel().getSelectedItem() == "CADETBLUE") { scene.setFill(Color.CADETBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "CHARTREUSE") { scene.setFill(Color.CHARTREUSE); } else if (comboColor.getSelectionModel().getSelectedItem() == "CHOCOLATE") { scene.setFill(Color.CHOCOLATE); } else if (comboColor.getSelectionModel().getSelectedItem() == "CORAL") { scene.setFill(Color.CORAL); } else if (comboColor.getSelectionModel().getSelectedItem() == "CORNFLOWERBLUE") { scene.setFill(Color.CORNFLOWERBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "CORNSILK") { scene.setFill(Color.CORNSILK); } else if (comboColor.getSelectionModel().getSelectedItem() == "CRIMSON") { scene.setFill(Color.CRIMSON); } else if (comboColor.getSelectionModel().getSelectedItem() == "CYAN") { scene.setFill(Color.CYAN); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKBLUE") { scene.setFill(Color.DARKBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKCYAN") { scene.setFill(Color.DARKCYAN); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKGOLDENROD") { scene.setFill(Color.DARKGOLDENROD); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKGREY") { scene.setFill(Color.DARKGREY); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKGREEN") { scene.setFill(Color.DARKGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKKHAKI") { scene.setFill(Color.DARKKHAKI); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKMAGENTA") { scene.setFill(Color.DARKMAGENTA); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKOLIVEGREEN") { scene.setFill(Color.DARKOLIVEGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKORANGE") { scene.setFill(Color.DARKORANGE); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKORCHID") { scene.setFill(Color.DARKORCHID); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKRED") { scene.setFill(Color.DARKRED); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKSALMON") { scene.setFill(Color.DARKSALMON); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKSEAGREEN") { scene.setFill(Color.DARKSEAGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKSLATEBLUE") { scene.setFill(Color.DARKSLATEBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKSLATEGREY") { scene.setFill(Color.DARKSLATEGREY); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKTURQUOISE") { scene.setFill(Color.DARKTURQUOISE); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKVIOLET") { scene.setFill(Color.DARKVIOLET); } else if (comboColor.getSelectionModel().getSelectedItem() == "DEEPPINK") { scene.setFill(Color.DEEPPINK); } else if (comboColor.getSelectionModel().getSelectedItem() == "DEEPSKYBLUE") { scene.setFill(Color.DEEPSKYBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "DIMGREY") { scene.setFill(Color.DIMGREY); } else if (comboColor.getSelectionModel().getSelectedItem() == "DODGERBLUE") { scene.setFill(Color.DODGERBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "FIREBRICK") { scene.setFill(Color.FIREBRICK); } else if (comboColor.getSelectionModel().getSelectedItem() == "FLORALWHITE") { scene.setFill(Color.FLORALWHITE); } else if (comboColor.getSelectionModel().getSelectedItem() == "FORESTGREEN") { scene.setFill(Color.FORESTGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "FUCHSIA") { scene.setFill(Color.FUCHSIA); } else if (comboColor.getSelectionModel().getSelectedItem() == "GAINSBORO") { scene.setFill(Color.GAINSBORO); } else if (comboColor.getSelectionModel().getSelectedItem() == "GHOSTWHITE") { scene.setFill(Color.GHOSTWHITE); } else if (comboColor.getSelectionModel().getSelectedItem() == "GOLD") { scene.setFill(Color.GOLD); } else if (comboColor.getSelectionModel().getSelectedItem() == "GOLDENROD") { scene.setFill(Color.GOLDENROD); } else if (comboColor.getSelectionModel().getSelectedItem() == "GREEN") { scene.setFill(Color.GREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "GREENYELLOW") { scene.setFill(Color.GREENYELLOW); } else if (comboColor.getSelectionModel().getSelectedItem() == "GREY") { scene.setFill(Color.GREY); } else if (comboColor.getSelectionModel().getSelectedItem() == "HONEYDEW") { scene.setFill(Color.HONEYDEW); } else if (comboColor.getSelectionModel().getSelectedItem() == "HOTPINK") { scene.setFill(Color.HOTPINK); } else if (comboColor.getSelectionModel().getSelectedItem() == "INDIANRED") { scene.setFill(Color.INDIANRED); } else if (comboColor.getSelectionModel().getSelectedItem() == "INDIGO") { scene.setFill(Color.INDIGO); } else if (comboColor.getSelectionModel().getSelectedItem() == "IVORY") { scene.setFill(Color.IVORY); } else if (comboColor.getSelectionModel().getSelectedItem() == "KHAKI") { scene.setFill(Color.KHAKI); } else if (comboColor.getSelectionModel().getSelectedItem() == "LAVENDER") { scene.setFill(Color.LAVENDER); } else if (comboColor.getSelectionModel().getSelectedItem() == "LAVENDERBLUSH") { scene.setFill(Color.LAVENDERBLUSH); } else if (comboColor.getSelectionModel().getSelectedItem() == "LAWNGREEN") { scene.setFill(Color.LAWNGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "LEMONCHIFFON") { scene.setFill(Color.LEMONCHIFFON); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTBLUE") { scene.setFill(Color.LIGHTBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTCORAL") { scene.setFill(Color.LIGHTCORAL); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTCYAN") { scene.setFill(Color.LIGHTCYAN); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTGOLDENRODYELLOW") { scene.setFill(Color.LIGHTGOLDENRODYELLOW); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTGREEN") { scene.setFill(Color.LIGHTGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTGREY") { scene.setFill(Color.LIGHTGREY); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTPINK") { scene.setFill(Color.LIGHTPINK); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTSALMON") { scene.setFill(Color.LIGHTSALMON); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTSEAGREEN") { scene.setFill(Color.LIGHTSEAGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTSKYBLUE") { scene.setFill(Color.LIGHTSKYBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTSLATEGREY") { scene.setFill(Color.LIGHTSLATEGREY); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTSTEELBLUE") { scene.setFill(Color.LIGHTSTEELBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTYELLOW") { scene.setFill(Color.LIGHTYELLOW); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIME") { scene.setFill(Color.LIME); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIMEGREEN") { scene.setFill(Color.LIMEGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "LINEN") { scene.setFill(Color.LINEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "MAGENTA") { scene.setFill(Color.MAGENTA); } else if (comboColor.getSelectionModel().getSelectedItem() == "MAROON") { scene.setFill(Color.MAROON); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMAQUAMARINE") { scene.setFill(Color.MEDIUMAQUAMARINE); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMBLUE") { scene.setFill(Color.MEDIUMBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMORCHID") { scene.setFill(Color.MEDIUMORCHID); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMPURPLE") { scene.setFill(Color.MEDIUMPURPLE); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMSEAGREEN") { scene.setFill(Color.MEDIUMSEAGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMSLATEBLUE") { scene.setFill(Color.MEDIUMSLATEBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMSPRINGGREEN") { scene.setFill(Color.MEDIUMSPRINGGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMTURQUOISE") { scene.setFill(Color.MEDIUMTURQUOISE); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMVIOLETRED") { scene.setFill(Color.MEDIUMVIOLETRED); } else if (comboColor.getSelectionModel().getSelectedItem() == "MIDNIGHTBLUE") { scene.setFill(Color.MIDNIGHTBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "MINTCREAM") { scene.setFill(Color.MINTCREAM); } else if (comboColor.getSelectionModel().getSelectedItem() == "MISTYROSE") { scene.setFill(Color.MISTYROSE); } else if (comboColor.getSelectionModel().getSelectedItem() == "MOCCASIN") { scene.setFill(Color.MOCCASIN); } else if (comboColor.getSelectionModel().getSelectedItem() == "NAVAJOWHITE") { scene.setFill(Color.NAVAJOWHITE); } else if (comboColor.getSelectionModel().getSelectedItem() == "NAVY") { scene.setFill(Color.NAVY); } else if (comboColor.getSelectionModel().getSelectedItem() == "OLDLACE") { scene.setFill(Color.OLDLACE); } else if (comboColor.getSelectionModel().getSelectedItem() == "OLIVE") { scene.setFill(Color.OLIVE); } else if (comboColor.getSelectionModel().getSelectedItem() == "OLIVEDRAB") { scene.setFill(Color.OLIVEDRAB); } else if (comboColor.getSelectionModel().getSelectedItem() == "ORANGE") { scene.setFill(Color.ORANGE); } else if (comboColor.getSelectionModel().getSelectedItem() == "ORANGERED") { scene.setFill(Color.ORANGERED); } else if (comboColor.getSelectionModel().getSelectedItem() == "ORCHID") { scene.setFill(Color.ORCHID); } else if (comboColor.getSelectionModel().getSelectedItem() == "PALEGOLDENROD") { scene.setFill(Color.PALEGOLDENROD); } else if (comboColor.getSelectionModel().getSelectedItem() == "PALEGREEN") { scene.setFill(Color.PALEGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "PALETURQUOISE") { scene.setFill(Color.PALETURQUOISE); } else if (comboColor.getSelectionModel().getSelectedItem() == "PALEVIOLETRED") { scene.setFill(Color.PALEVIOLETRED); } else if (comboColor.getSelectionModel().getSelectedItem() == "PAPAYAWHIP") { scene.setFill(Color.PAPAYAWHIP); } else if (comboColor.getSelectionModel().getSelectedItem() == "PEACHPUFF") { scene.setFill(Color.PEACHPUFF); } else if (comboColor.getSelectionModel().getSelectedItem() == "PERU") { scene.setFill(Color.PERU); } else if (comboColor.getSelectionModel().getSelectedItem() == "PINK") { scene.setFill(Color.PINK); } else if (comboColor.getSelectionModel().getSelectedItem() == "PLUM") { scene.setFill(Color.PLUM); } else if (comboColor.getSelectionModel().getSelectedItem() == "POWDERBLUE") { scene.setFill(Color.POWDERBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "PURPLE") { scene.setFill(Color.PURPLE); } else if (comboColor.getSelectionModel().getSelectedItem() == "RED") { scene.setFill(Color.RED); } else if (comboColor.getSelectionModel().getSelectedItem() == "ROSYBROWN") { scene.setFill(Color.ROSYBROWN); } else if (comboColor.getSelectionModel().getSelectedItem() == "ROYALBLUE") { scene.setFill(Color.ROYALBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "SADDLEBROWN") { scene.setFill(Color.SADDLEBROWN); } else if (comboColor.getSelectionModel().getSelectedItem() == "SALMON") { scene.setFill(Color.SALMON); } else if (comboColor.getSelectionModel().getSelectedItem() == "SANDYBROWN") { scene.setFill(Color.SANDYBROWN); } else if (comboColor.getSelectionModel().getSelectedItem() == "SEAGREEN") { scene.setFill(Color.SEAGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "SEASHELL") { scene.setFill(Color.SEASHELL); } else if (comboColor.getSelectionModel().getSelectedItem() == "SIENNA") { scene.setFill(Color.SIENNA); } else if (comboColor.getSelectionModel().getSelectedItem() == "SILVER") { scene.setFill(Color.SILVER); } else if (comboColor.getSelectionModel().getSelectedItem() == "SKYBLUE") { scene.setFill(Color.SKYBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "SLATEBLUE") { scene.setFill(Color.SLATEBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "SLATEGREY") { scene.setFill(Color.SLATEGREY); } else if (comboColor.getSelectionModel().getSelectedItem() == "SNOW") { scene.setFill(Color.SNOW); } else if (comboColor.getSelectionModel().getSelectedItem() == "SPRINGGREEN") { scene.setFill(Color.SPRINGGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "STEELBLUE") { scene.setFill(Color.STEELBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "TAN") { scene.setFill(Color.TAN); } else if (comboColor.getSelectionModel().getSelectedItem() == "TEAL") { scene.setFill(Color.TEAL); } else if (comboColor.getSelectionModel().getSelectedItem() == "THISTLE") { scene.setFill(Color.THISTLE); } else if (comboColor.getSelectionModel().getSelectedItem() == "TOMATO") { scene.setFill(Color.TOMATO); } else if (comboColor.getSelectionModel().getSelectedItem() == "TRANSPARENT") { scene.setFill(Color.TRANSPARENT); } else if (comboColor.getSelectionModel().getSelectedItem() == "TURQUOISE") { scene.setFill(Color.TURQUOISE); } else if (comboColor.getSelectionModel().getSelectedItem() == "VIOLET") { scene.setFill(Color.VIOLET); } else if (comboColor.getSelectionModel().getSelectedItem() == "WHEAT") { scene.setFill(Color.WHEAT); } else if (comboColor.getSelectionModel().getSelectedItem() == "WHITE") { scene.setFill(Color.WHITE); } else if (comboColor.getSelectionModel().getSelectedItem() == "WHITESMOKE") { scene.setFill(Color.WHITESMOKE); } else if (comboColor.getSelectionModel().getSelectedItem() == "YELLOW") { scene.setFill(Color.YELLOW); } else if (comboColor.getSelectionModel().getSelectedItem() == "YELLOWGREEN") { scene.setFill(Color.YELLOWGREEN); } } else { int convertedFontSize = 16; root.getChildren().remove(textLabel); try { convertedFontSize = Integer.parseInt(textSize.getText());} catch (Exception ex) { } if(comboColor.getSelectionModel().getSelectedItem() == "ALICEBLUE") { textLabel.setTextFill(Color.ALICEBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "ANTIQUEWHITE") { textLabel.setTextFill(Color.ANTIQUEWHITE); } else if (comboColor.getSelectionModel().getSelectedItem() == "AQUA") { textLabel.setTextFill(Color.AQUA); } else if (comboColor.getSelectionModel().getSelectedItem() == "AQUAMARINE") { textLabel.setTextFill(Color.AQUAMARINE); } else if (comboColor.getSelectionModel().getSelectedItem() == "AZURE") { textLabel.setTextFill(Color.AZURE); } else if (comboColor.getSelectionModel().getSelectedItem() == "BEIGE") { textLabel.setTextFill(Color.BEIGE); } else if (comboColor.getSelectionModel().getSelectedItem() == "BISQUE") { textLabel.setTextFill(Color.BISQUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "BLACK") { textLabel.setTextFill(Color.BLACK); } else if (comboColor.getSelectionModel().getSelectedItem() == "BLANCHEDALMOND") { textLabel.setTextFill(Color.BLANCHEDALMOND); } else if (comboColor.getSelectionModel().getSelectedItem() == "BLUE") { textLabel.setTextFill(Color.BLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "BLUEVIOLET") { textLabel.setTextFill(Color.BLUEVIOLET); } else if (comboColor.getSelectionModel().getSelectedItem() == "BROWN") { textLabel.setTextFill(Color.BROWN); } else if (comboColor.getSelectionModel().getSelectedItem() == "BURLYWOOD") { textLabel.setTextFill(Color.BURLYWOOD); } else if (comboColor.getSelectionModel().getSelectedItem() == "CADETBLUE") { textLabel.setTextFill(Color.CADETBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "CHARTREUSE") { textLabel.setTextFill(Color.CHARTREUSE); } else if (comboColor.getSelectionModel().getSelectedItem() == "CHOCOLATE") { textLabel.setTextFill(Color.CHOCOLATE); } else if (comboColor.getSelectionModel().getSelectedItem() == "CORAL") { textLabel.setTextFill(Color.CORAL); } else if (comboColor.getSelectionModel().getSelectedItem() == "CORNFLOWERBLUE") { textLabel.setTextFill(Color.CORNFLOWERBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "CORNSILK") { textLabel.setTextFill(Color.CORNSILK); } else if (comboColor.getSelectionModel().getSelectedItem() == "CRIMSON") { textLabel.setTextFill(Color.CRIMSON); } else if (comboColor.getSelectionModel().getSelectedItem() == "CYAN") { textLabel.setTextFill(Color.CYAN); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKBLUE") { textLabel.setTextFill(Color.DARKBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKCYAN") { textLabel.setTextFill(Color.DARKCYAN); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKGOLDENROD") { textLabel.setTextFill(Color.DARKGOLDENROD); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKGREY") { textLabel.setTextFill(Color.DARKGREY); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKGREEN") { textLabel.setTextFill(Color.DARKGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKKHAKI") { textLabel.setTextFill(Color.DARKKHAKI); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKMAGENTA") { textLabel.setTextFill(Color.DARKMAGENTA); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKOLIVEGREEN") { textLabel.setTextFill(Color.DARKOLIVEGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKORANGE") { textLabel.setTextFill(Color.DARKORANGE); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKORCHID") { textLabel.setTextFill(Color.DARKORCHID); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKRED") { textLabel.setTextFill(Color.DARKRED); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKSALMON") { textLabel.setTextFill(Color.DARKSALMON); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKSEAGREEN") { textLabel.setTextFill(Color.DARKSEAGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKSLATEBLUE") { textLabel.setTextFill(Color.DARKSLATEBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKSLATEGREY") { textLabel.setTextFill(Color.DARKSLATEGREY); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKTURQUOISE") { textLabel.setTextFill(Color.DARKTURQUOISE); } else if (comboColor.getSelectionModel().getSelectedItem() == "DARKVIOLET") { textLabel.setTextFill(Color.DARKVIOLET); } else if (comboColor.getSelectionModel().getSelectedItem() == "DEEPPINK") { textLabel.setTextFill(Color.DEEPPINK); } else if (comboColor.getSelectionModel().getSelectedItem() == "DEEPSKYBLUE") { textLabel.setTextFill(Color.DEEPSKYBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "DIMGREY") { textLabel.setTextFill(Color.DIMGREY); } else if (comboColor.getSelectionModel().getSelectedItem() == "DODGERBLUE") { textLabel.setTextFill(Color.DODGERBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "FIREBRICK") { textLabel.setTextFill(Color.FIREBRICK); } else if (comboColor.getSelectionModel().getSelectedItem() == "FLORALWHITE") { textLabel.setTextFill(Color.FLORALWHITE); } else if (comboColor.getSelectionModel().getSelectedItem() == "FORESTGREEN") { textLabel.setTextFill(Color.FORESTGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "FUCHSIA") { textLabel.setTextFill(Color.FUCHSIA); } else if (comboColor.getSelectionModel().getSelectedItem() == "GAINSBORO") { textLabel.setTextFill(Color.GAINSBORO); } else if (comboColor.getSelectionModel().getSelectedItem() == "GHOSTWHITE") { textLabel.setTextFill(Color.GHOSTWHITE); } else if (comboColor.getSelectionModel().getSelectedItem() == "GOLD") { textLabel.setTextFill(Color.GOLD); } else if (comboColor.getSelectionModel().getSelectedItem() == "GOLDENROD") { textLabel.setTextFill(Color.GOLDENROD); } else if (comboColor.getSelectionModel().getSelectedItem() == "GREEN") { textLabel.setTextFill(Color.GREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "GREENYELLOW") { textLabel.setTextFill(Color.GREENYELLOW); } else if (comboColor.getSelectionModel().getSelectedItem() == "GREY") { textLabel.setTextFill(Color.GREY); } else if (comboColor.getSelectionModel().getSelectedItem() == "HONEYDEW") { textLabel.setTextFill(Color.HONEYDEW); } else if (comboColor.getSelectionModel().getSelectedItem() == "HOTPINK") { textLabel.setTextFill(Color.HOTPINK); } else if (comboColor.getSelectionModel().getSelectedItem() == "INDIANRED") { textLabel.setTextFill(Color.INDIANRED); } else if (comboColor.getSelectionModel().getSelectedItem() == "INDIGO") { textLabel.setTextFill(Color.INDIGO); } else if (comboColor.getSelectionModel().getSelectedItem() == "IVORY") { textLabel.setTextFill(Color.IVORY); } else if (comboColor.getSelectionModel().getSelectedItem() == "KHAKI") { textLabel.setTextFill(Color.KHAKI); } else if (comboColor.getSelectionModel().getSelectedItem() == "LAVENDER") { textLabel.setTextFill(Color.LAVENDER); } else if (comboColor.getSelectionModel().getSelectedItem() == "LAVENDERBLUSH") { textLabel.setTextFill(Color.LAVENDERBLUSH); } else if (comboColor.getSelectionModel().getSelectedItem() == "LAWNGREEN") { textLabel.setTextFill(Color.LAWNGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "LEMONCHIFFON") { textLabel.setTextFill(Color.LEMONCHIFFON); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTBLUE") { textLabel.setTextFill(Color.LIGHTBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTCORAL") { textLabel.setTextFill(Color.LIGHTCORAL); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTCYAN") { textLabel.setTextFill(Color.LIGHTCYAN); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTGOLDENRODYELLOW") { textLabel.setTextFill(Color.LIGHTGOLDENRODYELLOW); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTGREEN") { textLabel.setTextFill(Color.LIGHTGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTGREY") { textLabel.setTextFill(Color.LIGHTGREY); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTPINK") { textLabel.setTextFill(Color.LIGHTPINK); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTSALMON") { textLabel.setTextFill(Color.LIGHTSALMON); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTSEAGREEN") { textLabel.setTextFill(Color.LIGHTSEAGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTSKYBLUE") { textLabel.setTextFill(Color.LIGHTSKYBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTSLATEGREY") { textLabel.setTextFill(Color.LIGHTSLATEGREY); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTSTEELBLUE") { textLabel.setTextFill(Color.LIGHTSTEELBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIGHTYELLOW") { textLabel.setTextFill(Color.LIGHTYELLOW); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIME") { textLabel.setTextFill(Color.LIME); } else if (comboColor.getSelectionModel().getSelectedItem() == "LIMEGREEN") { textLabel.setTextFill(Color.LIMEGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "LINEN") { textLabel.setTextFill(Color.LINEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "MAGENTA") { textLabel.setTextFill(Color.MAGENTA); } else if (comboColor.getSelectionModel().getSelectedItem() == "MAROON") { textLabel.setTextFill(Color.MAROON); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMAQUAMARINE") { textLabel.setTextFill(Color.MEDIUMAQUAMARINE); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMBLUE") { textLabel.setTextFill(Color.MEDIUMBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMORCHID") { textLabel.setTextFill(Color.MEDIUMORCHID); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMPURPLE") { textLabel.setTextFill(Color.MEDIUMPURPLE); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMSEAGREEN") { textLabel.setTextFill(Color.MEDIUMSEAGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMSLATEBLUE") { textLabel.setTextFill(Color.MEDIUMSLATEBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMSPRINGGREEN") { textLabel.setTextFill(Color.MEDIUMSPRINGGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMTURQUOISE") { textLabel.setTextFill(Color.MEDIUMTURQUOISE); } else if (comboColor.getSelectionModel().getSelectedItem() == "MEDIUMVIOLETRED") { textLabel.setTextFill(Color.MEDIUMVIOLETRED); } else if (comboColor.getSelectionModel().getSelectedItem() == "MIDNIGHTBLUE") { textLabel.setTextFill(Color.MIDNIGHTBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "MINTCREAM") { textLabel.setTextFill(Color.MINTCREAM); } else if (comboColor.getSelectionModel().getSelectedItem() == "MISTYROSE") { textLabel.setTextFill(Color.MISTYROSE); } else if (comboColor.getSelectionModel().getSelectedItem() == "MOCCASIN") { textLabel.setTextFill(Color.MOCCASIN); } else if (comboColor.getSelectionModel().getSelectedItem() == "NAVAJOWHITE") { textLabel.setTextFill(Color.NAVAJOWHITE); } else if (comboColor.getSelectionModel().getSelectedItem() == "NAVY") { textLabel.setTextFill(Color.NAVY); } else if (comboColor.getSelectionModel().getSelectedItem() == "OLDLACE") { textLabel.setTextFill(Color.OLDLACE); } else if (comboColor.getSelectionModel().getSelectedItem() == "OLIVE") { textLabel.setTextFill(Color.OLIVE); } else if (comboColor.getSelectionModel().getSelectedItem() == "OLIVEDRAB") { textLabel.setTextFill(Color.OLIVEDRAB); } else if (comboColor.getSelectionModel().getSelectedItem() == "ORANGE") { textLabel.setTextFill(Color.ORANGE); } else if (comboColor.getSelectionModel().getSelectedItem() == "ORANGERED") { textLabel.setTextFill(Color.ORANGERED); } else if (comboColor.getSelectionModel().getSelectedItem() == "ORCHID") { textLabel.setTextFill(Color.ORCHID); } else if (comboColor.getSelectionModel().getSelectedItem() == "PALEGOLDENROD") { textLabel.setTextFill(Color.PALEGOLDENROD); } else if (comboColor.getSelectionModel().getSelectedItem() == "PALEGREEN") { textLabel.setTextFill(Color.PALEGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "PALETURQUOISE") { textLabel.setTextFill(Color.PALETURQUOISE); } else if (comboColor.getSelectionModel().getSelectedItem() == "PALEVIOLETRED") { textLabel.setTextFill(Color.PALEVIOLETRED); } else if (comboColor.getSelectionModel().getSelectedItem() == "PAPAYAWHIP") { textLabel.setTextFill(Color.PAPAYAWHIP); } else if (comboColor.getSelectionModel().getSelectedItem() == "PEACHPUFF") { textLabel.setTextFill(Color.PEACHPUFF); } else if (comboColor.getSelectionModel().getSelectedItem() == "PERU") { textLabel.setTextFill(Color.PERU); } else if (comboColor.getSelectionModel().getSelectedItem() == "PINK") { textLabel.setTextFill(Color.PINK); } else if (comboColor.getSelectionModel().getSelectedItem() == "PLUM") { textLabel.setTextFill(Color.PLUM); } else if (comboColor.getSelectionModel().getSelectedItem() == "POWDERBLUE") { textLabel.setTextFill(Color.POWDERBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "PURPLE") { textLabel.setTextFill(Color.PURPLE); } else if (comboColor.getSelectionModel().getSelectedItem() == "RED") { textLabel.setTextFill(Color.RED); } else if (comboColor.getSelectionModel().getSelectedItem() == "ROSYBROWN") { textLabel.setTextFill(Color.ROSYBROWN); } else if (comboColor.getSelectionModel().getSelectedItem() == "ROYALBLUE") { textLabel.setTextFill(Color.ROYALBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "SADDLEBROWN") { textLabel.setTextFill(Color.SADDLEBROWN); } else if (comboColor.getSelectionModel().getSelectedItem() == "SALMON") { textLabel.setTextFill(Color.SALMON); } else if (comboColor.getSelectionModel().getSelectedItem() == "SANDYBROWN") { textLabel.setTextFill(Color.SANDYBROWN); } else if (comboColor.getSelectionModel().getSelectedItem() == "SEAGREEN") { textLabel.setTextFill(Color.SEAGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "SEASHELL") { textLabel.setTextFill(Color.SEASHELL); } else if (comboColor.getSelectionModel().getSelectedItem() == "SIENNA") { textLabel.setTextFill(Color.SIENNA); } else if (comboColor.getSelectionModel().getSelectedItem() == "SILVER") { textLabel.setTextFill(Color.SILVER); } else if (comboColor.getSelectionModel().getSelectedItem() == "SKYBLUE") { textLabel.setTextFill(Color.SKYBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "SLATEBLUE") { textLabel.setTextFill(Color.SLATEBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "SLATEGREY") { textLabel.setTextFill(Color.SLATEGREY); } else if (comboColor.getSelectionModel().getSelectedItem() == "SNOW") { textLabel.setTextFill(Color.SNOW); } else if (comboColor.getSelectionModel().getSelectedItem() == "SPRINGGREEN") { textLabel.setTextFill(Color.SPRINGGREEN); } else if (comboColor.getSelectionModel().getSelectedItem() == "STEELBLUE") { textLabel.setTextFill(Color.STEELBLUE); } else if (comboColor.getSelectionModel().getSelectedItem() == "TAN") { textLabel.setTextFill(Color.TAN); } else if (comboColor.getSelectionModel().getSelectedItem() == "TEAL") { textLabel.setTextFill(Color.TEAL); } else if (comboColor.getSelectionModel().getSelectedItem() == "THISTLE") { textLabel.setTextFill(Color.THISTLE); } else if (comboColor.getSelectionModel().getSelectedItem() == "TOMATO") { textLabel.setTextFill(Color.TOMATO); } else if (comboColor.getSelectionModel().getSelectedItem() == "TRANSPARENT") { textLabel.setTextFill(Color.TRANSPARENT); } else if (comboColor.getSelectionModel().getSelectedItem() == "TURQUOISE") { textLabel.setTextFill(Color.TURQUOISE); } else if (comboColor.getSelectionModel().getSelectedItem() == "VIOLET") { textLabel.setTextFill(Color.VIOLET); } else if (comboColor.getSelectionModel().getSelectedItem() == "WHEAT") { textLabel.setTextFill(Color.WHEAT); } else if (comboColor.getSelectionModel().getSelectedItem() == "WHITE") { textLabel.setTextFill(Color.WHITE); } else if (comboColor.getSelectionModel().getSelectedItem() == "WHITESMOKE") { textLabel.setTextFill(Color.WHITESMOKE); } else if (comboColor.getSelectionModel().getSelectedItem() == "YELLOW") { textLabel.setTextFill(Color.YELLOW); } else if (comboColor.getSelectionModel().getSelectedItem() == "YELLOWGREEN") { textLabel.setTextFill(Color.YELLOWGREEN); } root.getChildren().add(textLabel); } }); scene.setOnMousePressed(e -> { if(e.isSecondaryButtonDown()) { int convertedFontSize = 16; String stringX = String.valueOf(e.getSceneX()); String stringY = String.valueOf(e.getSceneY()); sceneXLabel.setText(" "+ stringX + " " + stringY); copyConfirmed.setVisible(true); String labelVarCopy2 = copyTextField.getText(); String clipboardCopy2 = labelVarCopy2 + ".setLayoutX(" + stringX + ");" + '\n' + labelVarCopy2 + ".setLayoutY(" + stringY + ");"; StringSelection stringSelection2 = new StringSelection(clipboardCopy2); Clipboard clpbrd2 = Toolkit.getDefaultToolkit().getSystemClipboard(); clpbrd2.setContents(stringSelection2, null); Timeline timeline2 = new Timeline(new KeyFrame( Duration.millis(2500), ae -> { copyConfirmed.setVisible(false); })); timeline2.play(); } else if (e.isPrimaryButtonDown()){ int convertedFontSize = 16; String stringX = String.valueOf(e.getSceneX()); String stringY = String.valueOf(e.getSceneY()); sceneXLabel.setText(" "+ stringX + " " + stringY); textLabel.setLayoutX(e.getSceneX()); textLabel.setLayoutY(e.getSceneY()); } }); textSize.setOnKeyReleased(e -> { int convertedFontSize = 16; root.getChildren().remove(textLabel); try { convertedFontSize = Integer.parseInt(textSize.getText());} catch (Exception ex) { } textLabel.setFont(new Font(comboFont.getSelectionModel().getSelectedItem(), convertedFontSize)); if(boldCheckbox.isSelected()) { if(italicCheckbox.isSelected()) { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.BOLD, FontPosture.ITALIC, convertedFontSize)); } else { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.BOLD, FontPosture.REGULAR, convertedFontSize)); } } else { if(italicCheckbox.isSelected()) { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.NORMAL,FontPosture.ITALIC, convertedFontSize)); } else { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.NORMAL,FontPosture.REGULAR, convertedFontSize)); } } root.getChildren().add(textLabel); }); comboFont.setOnAction(e -> { int convertedFontSize = 16; root.getChildren().remove(textLabel); try { convertedFontSize = Integer.parseInt(textSize.getText());} catch (Exception ex) { } if(boldCheckbox.isSelected()) { if(italicCheckbox.isSelected()) { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.BOLD, FontPosture.ITALIC, convertedFontSize)); } else { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.BOLD, FontPosture.REGULAR, convertedFontSize)); } } else { if(italicCheckbox.isSelected()) { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.NORMAL,FontPosture.ITALIC, convertedFontSize)); } else { textLabel.setFont(Font.font(comboFont.getSelectionModel().getSelectedItem(), FontWeight.NORMAL,FontPosture.REGULAR, convertedFontSize)); } } root.getChildren().add(textLabel); }); Timeline timeline = new Timeline(new KeyFrame( Duration.millis(1), ae -> { root.getChildren().remove(textProperties); textProperties.setLayoutX(24.0); textProperties.setLayoutY(sceneHeight + 15); root.getChildren().add(textProperties); })); timeline.play(); }
	
}
