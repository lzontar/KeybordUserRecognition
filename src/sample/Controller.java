package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import static java.math.BigDecimal.ROUND_HALF_UP;
import org.apache.commons.math3.linear.*;

public class Controller {

    KeyCode prevKeyCodeTyped;
    BigDecimal prevTimeReleased;
    int ixPrevKey;

    KeyCode currKeyTyped;
    BigDecimal currTimePressed;
    int ixCurrKey;

    //26 letters, 10 digits, comma, period and space
    RealMatrix charCombMat;
    BigDecimal[][] charCombTimeSum = new BigDecimal[26 + 10 + 1 + 1 + 1][26 + 10 + 1 + 1 + 1];
    int[][] charCombReps = new int[26 + 10 + 1 + 1 + 1][26 + 10 + 1 + 1 + 1];

    BigDecimal pressedTime;

    RealMatrix keyStrokeMat;
    BigDecimal[] keyStrokeTimeSum = new BigDecimal[26 + 10 + 1 + 1 + 1];
    int[] keyStrokeTimeReps = new int[26 + 10 + 1 + 1 + 1];


    int nOfBackSpaces = 0;

    @FXML
    public void initialize() {
        for(int i = 0; i < charCombTimeSum.length; i++) {
            for(int j = 0; j < charCombTimeSum[i].length; j++) {
                charCombTimeSum[i][j] = BigDecimal.ZERO;
            }
            keyStrokeTimeSum[i] = BigDecimal.ZERO;
        }
        nOfBackSpaces = 0;
        charCombMat = new Array2DRowRealMatrix(39,39);
        keyStrokeMat = new Array2DRowRealMatrix(39,1);

    }

    @FXML
    public void keyPressed(KeyEvent event) {
        pressedTime = new BigDecimal(System.nanoTime());

        currKeyTyped = event.getCode();
        currTimePressed = new BigDecimal(System.nanoTime());

        ixCurrKey = -1;
        if(currKeyTyped.equals(KeyCode.BACK_SPACE)) {
            nOfBackSpaces++;
            prevKeyCodeTyped = null;
        } else if (currKeyTyped.isLetterKey() || currKeyTyped.isDigitKey() || currKeyTyped.equals(KeyCode.PERIOD) || currKeyTyped.equals(KeyCode.COMMA) || currKeyTyped.equals(KeyCode.SPACE)) {
            if(prevKeyCodeTyped != null && prevTimeReleased != null) {
                int asciiCode = currKeyTyped.impl_getCode();
                if(currKeyTyped.isLetterKey()) {
                    ixCurrKey = currKeyTyped.impl_getCode() >= 97 ? currKeyTyped.impl_getCode() - 97 : currKeyTyped.impl_getCode() - 65;

                    if(prevKeyCodeTyped.isLetterKey()) {
                        ixPrevKey = prevKeyCodeTyped.impl_getCode() >= 97 ? prevKeyCodeTyped.impl_getCode() - 97 : prevKeyCodeTyped.impl_getCode() - 65;

                    } else if(prevKeyCodeTyped.isDigitKey()) {
                        //+ 26 because first 26 elements represent letters
                        ixPrevKey = 26 + prevKeyCodeTyped.impl_getCode() - 48;

                    } else if (prevKeyCodeTyped.equals(KeyCode.PERIOD)) {
                        ixPrevKey = 36;
                    } else if (prevKeyCodeTyped.equals(KeyCode.COMMA)) {
                        ixPrevKey = 37;
                    } else if (prevKeyCodeTyped.equals(KeyCode.SPACE)) {
                        ixPrevKey = 38;
                    }
                } else if(currKeyTyped.isDigitKey()) {
                    ixCurrKey = 26 + currKeyTyped.impl_getCode() - 48;
                    if(prevKeyCodeTyped.isLetterKey()) {
                        ixPrevKey = prevKeyCodeTyped.impl_getCode() >= 97 ? prevKeyCodeTyped.impl_getCode() - 97 : prevKeyCodeTyped.impl_getCode() - 65;

                    } else if(prevKeyCodeTyped.isDigitKey()) {
                        //+ 26 because first 26 elements represent letters
                        ixPrevKey = 26 + prevKeyCodeTyped.impl_getCode() - 48;

                    } else if (prevKeyCodeTyped.equals(KeyCode.PERIOD)) {
                        ixPrevKey = 36;
                    } else if (prevKeyCodeTyped.equals(KeyCode.COMMA)) {
                        ixPrevKey = 37;
                    } else if (prevKeyCodeTyped.equals(KeyCode.SPACE)) {
                        ixPrevKey = 38;
                    }
                } else if (currKeyTyped.equals(KeyCode.PERIOD)) {
                    ixCurrKey = 36;
                    if(prevKeyCodeTyped.isLetterKey()) {
                        ixPrevKey = prevKeyCodeTyped.impl_getCode() >= 97 ? prevKeyCodeTyped.impl_getCode() - 97 : prevKeyCodeTyped.impl_getCode() - 65;

                    } else if(prevKeyCodeTyped.isDigitKey()) {
                        //+ 26 because first 26 elements represent letters
                        ixPrevKey = 26 + prevKeyCodeTyped.impl_getCode() - 48;

                    } else if (prevKeyCodeTyped.equals(KeyCode.PERIOD)) {
                        ixPrevKey = 36;
                    } else if (prevKeyCodeTyped.equals(KeyCode.COMMA)) {
                        ixPrevKey = 37;
                    } else if (prevKeyCodeTyped.equals(KeyCode.SPACE)) {
                        ixPrevKey = 38;
                    }
                } else if (currKeyTyped.equals(KeyCode.COMMA)) {
                    ixCurrKey = 37;
                    if(prevKeyCodeTyped.isLetterKey()) {
                        ixPrevKey = prevKeyCodeTyped.impl_getCode() >= 97 ? prevKeyCodeTyped.impl_getCode() - 97 : prevKeyCodeTyped.impl_getCode() - 65;

                    } else if(prevKeyCodeTyped.isDigitKey()) {
                        //+ 26 because first 26 elements represent letters
                        ixPrevKey = 26 + prevKeyCodeTyped.impl_getCode() - 48;

                    } else if (prevKeyCodeTyped.equals(KeyCode.PERIOD)) {
                        ixPrevKey = 36;
                    } else if (prevKeyCodeTyped.equals(KeyCode.COMMA)) {
                        ixPrevKey = 37;
                    } else if (prevKeyCodeTyped.equals(KeyCode.SPACE)) {
                        ixPrevKey = 38;
                    }
                } else if (currKeyTyped.equals(KeyCode.SPACE)) {
                    ixCurrKey = 38;
                    if(prevKeyCodeTyped.isLetterKey()) {
                        ixPrevKey = prevKeyCodeTyped.impl_getCode() >= 97 ? prevKeyCodeTyped.impl_getCode() - 97 : prevKeyCodeTyped.impl_getCode() - 65;

                    } else if(prevKeyCodeTyped.isDigitKey()) {
                        //+ 26 because first 26 elements represent letters
                        ixPrevKey = 26 + prevKeyCodeTyped.impl_getCode() - 48;

                    } else if (prevKeyCodeTyped.equals(KeyCode.PERIOD)) {
                        ixPrevKey = 36;
                    } else if (prevKeyCodeTyped.equals(KeyCode.COMMA)) {
                        ixPrevKey = 37;
                    } else if (prevKeyCodeTyped.equals(KeyCode.SPACE)) {
                        ixPrevKey = 38;
                    }
                }
                try {
                    if(ixCurrKey != -1) {
                        charCombTimeSum[ixCurrKey][ixPrevKey] = (charCombTimeSum[ixCurrKey][ixPrevKey]).add(currTimePressed.subtract(prevTimeReleased));
                        charCombReps[ixCurrKey][ixPrevKey]++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            prevKeyCodeTyped = currKeyTyped;
        } else {
            prevKeyCodeTyped = null;
        }
    }
    @FXML
    public void keyReleased(KeyEvent event) {
        BigDecimal keyStrokeTime = (new BigDecimal(System.nanoTime())).subtract(pressedTime);

        KeyCode currKey = event.getCode();
        ixCurrKey = -1;
        if(currKey.isLetterKey()) {
            ixCurrKey = currKey.impl_getCode() >= 97 ? currKey.impl_getCode() - 97 : currKey.impl_getCode() - 65;

        } else if(currKey.isDigitKey()) {
            //+ 26 because first 26 elements represent letters
            ixCurrKey = 26 + currKey.impl_getCode() - 48;

        } else if (currKey.equals(KeyCode.PERIOD)) {
            ixCurrKey = 36;
        } else if (currKey.equals(KeyCode.COMMA)) {
            ixCurrKey = 37;
        } else if (currKey.equals(KeyCode.SPACE)) {
            ixCurrKey = 38;
        }
        try {
            if(ixCurrKey != -1) {
                keyStrokeTimeSum[ixCurrKey] = keyStrokeTimeSum[ixCurrKey].add(keyStrokeTime);
                keyStrokeTimeReps[ixCurrKey]++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        prevTimeReleased = currTimePressed;
    }

    public void saveData() {
        try {
           /*for (int i = 0; i < charCombTimeSum.length; i++) {
                for (int j = 0; j < charCombTimeSum[i].length; j++) {
                    BigDecimal ijAvgTime = charCombReps[i][j] != 0 ? charCombTimeSum[i][j].divide(new BigDecimal(charCombReps[i][j]).multiply(new BigDecimal(1_000_000_000)), 6, ROUND_HALF_UP) : BigDecimal.ZERO;
                    System.out.print(ijAvgTime.toString() + " ");
                }
                System.out.println();
            }
            System.out.println();
            for (int i = 0; i < charCombTimeSum.length; i++) {
                BigDecimal iAvgTime = keyStrokeTimeReps[i] != 0 ? keyStrokeTimeSum[i].divide(new BigDecimal(keyStrokeTimeReps[i]).multiply(new BigDecimal(1_000_000_000)), 6, ROUND_HALF_UP) : BigDecimal.ZERO;
                System.out.print(iAvgTime.toString() + " ");
            }
            System.out.println();
            System.out.println(nOfBackSpaces);*/

            File charCombinationFile = new File("charCombinationAvgTime.mat");

            BufferedWriter writer = new BufferedWriter(new FileWriter(charCombinationFile));
            for (int i = 0; i < charCombTimeSum.length; i++) {
                double[] row = new double[charCombTimeSum[i].length];
                for (int j = 0; j < charCombTimeSum[i].length; j++) {
                    BigDecimal ijAvgTime = charCombReps[i][j] != 0 ? charCombTimeSum[i][j].divide(new BigDecimal(charCombReps[i][j]).multiply(new BigDecimal(1_000_000_000)), 6, ROUND_HALF_UP) : BigDecimal.ZERO;
                    row[j] = ijAvgTime.doubleValue();
                    writer.write(ijAvgTime.toString() + " ");
                }
                charCombMat.setRow(i, row);
                writer.newLine();
            }
            writer.close();

            File keyStrokeFile = new File("keyStrokeAvgTime.mat");

            writer = new BufferedWriter(new FileWriter(keyStrokeFile));
            double[] keyStrokeRow = new double[keyStrokeTimeSum.length];
            for (int i = 0; i < keyStrokeTimeSum.length; i++) {
                BigDecimal iAvgTime = keyStrokeTimeReps[i] != 0 ? keyStrokeTimeSum[i].divide(new BigDecimal(keyStrokeTimeReps[i]).multiply(new BigDecimal(1_000_000_000)), 6, ROUND_HALF_UP) : BigDecimal.ZERO;
                keyStrokeRow[i] = iAvgTime.doubleValue();
                writer.write(iAvgTime.toString() + " ");
            }
            keyStrokeMat.setColumn( 0, keyStrokeRow);
            writer.close();

            File backspacesFile = new File("numBackspace.mat");

            writer = new BufferedWriter(new FileWriter(backspacesFile));
            writer.write(Integer.toString(nOfBackSpaces));
            writer.close();

            // ------------ PROCESS MATRICES -------------- //
            processData();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void processData() {
        if(charCombMat != null) {
            SingularValueDecomposition charCombSVD = new SingularValueDecomposition(charCombMat);
            double[] singularValues = charCombSVD.getSingularValues();

            for(int i = 0; i < singularValues.length; i++) {
                System.out.print(singularValues[i] + " ");
            }
            System.out.println();

        }
        if(keyStrokeMat != null) {
            SingularValueDecomposition keyStrokeSVD = new SingularValueDecomposition(keyStrokeMat);
            double[] singularValues = keyStrokeSVD.getSingularValues();
            
            for(int i = 0; i < singularValues.length; i++) {
                System.out.print(singularValues[i] + " ");
            }
            System.out.println();

        }
    }
}
