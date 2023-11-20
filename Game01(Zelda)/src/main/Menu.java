package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import world.World;

public class Menu {
    
    public String[] options = {"novo jogo", "carregar jogo", "sair"};
    public int currentOption = 0;
    public int maxOption = options.length - 1;

    public boolean up, down, enter;

    public static boolean pause = false;

    public static boolean saveExist = false;
    public static boolean saveGame = false;


    public void tick(){
        File file = new File("save.txt");
        if(file.exists()){
            saveExist = true;
        } else{
            saveExist = false;
        }
        if(up){
            up = false;
            currentOption--;
            if(currentOption < 0){
                currentOption = maxOption;
            }
        }

        if(down){
            down = false;
            currentOption++;
            if(currentOption > maxOption){
                currentOption = 0;
            }
        }

        if(enter){
            enter = false;
            if(options[currentOption] == "novo jogo" || options[currentOption] == "continuar"){
                Game.gameState = "Normal";
                pause = false;
                file = new File("save.txt");
                file.delete();
            } else if(options[currentOption] == "carregar jogo"){
                file = new File("save.txt");
                if(file.exists()){
                    String saver = loadGame(10);
                    applySave(saver);
                }
            }  else if(options[currentOption] == "sair"){
                System.exit(1);
            }
        }
    }

    public static void applySave(String str){
        String[] spl = str.split("/");
        for(int i = 0; i < spl.length; i++){
            String[] spl2 = spl[i].split(":");
            switch (spl2[0]) {
                case "level":
                    World.restartGame("level"+spl2[1]+".png");
                    Game.gameState = "Normal";
                    pause = false;
                    break;
                case "vida":
                    Game.player.life = Integer.parseInt(spl2[1]);
                    break;
            }
        }
    }

    public static String loadGame(int encode){
        String line = "";
        File file = new File("save.txt");
        if(file.exists()){
            try{
                String singleLine = null;
                BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
                try{
                    while((singleLine = reader.readLine()) != null){
                        String[] trans = singleLine.split(":");
                        char[] val = trans[1].toCharArray();
                        trans[1] = "";
                        for (int i = 0; i < val.length; i++){
                            val[i] -= encode;
                            trans[1] += val[i];
                        }
                        line+= trans[0];
                        line+=":";
                        line+=trans[1];
                        line+="/";
                    }
                } catch (IOException e){

                }
            } catch(FileNotFoundException e){}
        }

        return line;
    }

    public static void saveGame(String[] vall, int[] val2, int encode){
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter("save.txt"));
        } catch(IOException e){
            e.printStackTrace();
        }

        for (int i = 0; i < vall.length; i++){
            String current = vall[i];
            current+=":";
            char[] value = Integer.toString(val2[i]).toCharArray();
            for(int n = 0; n < value.length; n++){
                value[n]+=encode;
                current +=value[n];
            }
            try{
                writer.write(current);
                if(i < vall.length - 1){
                    writer.newLine();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        try{
            writer.flush();
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void render(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
        
        g.setColor(Color.RED);
        g.setFont(new Font("arial", Font.BOLD, 36));
        g.drawString("<Danki Code>", (Game.WIDTH*Game.SCALE) / 2 - 110, (Game.HEIGHT*Game.SCALE) / 2 - 160);

        //Opçoes do menu
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 24));
        if(pause == false){
            g.drawString("Novo Jogo", (Game.WIDTH*Game.SCALE) / 2 - 50, 160);
        } else{
            g.drawString("Resumir", (Game.WIDTH*Game.SCALE) / 2 - 40, 160);
        }
        g.drawString("Carregar Jogo", (Game.WIDTH*Game.SCALE) / 2 - 70, 200);
        g.drawString("Sair", (Game.WIDTH*Game.SCALE) / 2 - 12, 240);

        if(options[currentOption] == "novo jogo"){
            g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 80, 160);
        } else if(options[currentOption] == "carregar jogo"){
            g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 100, 200);
        } else if(options[currentOption] == "sair"){
            g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 40, 240);
        }
    }
}
