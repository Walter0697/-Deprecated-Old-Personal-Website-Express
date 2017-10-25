import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.io.BufferedReader; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class BallGame extends PApplet {

  
  
  final int HEIGHT = 700;
  final int WIDTH = 500;
  final int CIR_DIAMETER = 15;
  
  final int START_SCREEN = 9999;
  final int GAME_SCREEN = 8888;
  final int SHOP_SCREEN = 7777;
  final int GAMEOVER_SCREEN = 6666;
  
  int mode = START_SCREEN;
  int highest = 0;
  int money = 0;
  TitleBar tb;
  Store st;
  
  int NUMBER = 1;
  int firsttouch = -1;
  int BallType = 0;
  
  Circle ci;
  BlockCreate b;
  int bnum = 0, cnum = 0;
  ArrayList blocks;
  ArrayList circles;
  
  int TouchAddTime = 0;
  boolean RoundUp = true;
  boolean AllTouch = true;
  boolean AllRun = false;
  boolean CleanUp = true;
  boolean AllTool = false;
  
  boolean RoadNow = false;
  
  int pointer = 0;
  int counttime = 0;
  boolean StartRun = false;
  int thatx, thaty;
  boolean isEnd = false;
  boolean AddMoney = true;
  
  PrintWriter save;
  BufferedReader reader;
  
  public void setup()
  {
    
    tb = new TitleBar();
    st = new Store();
    
    blocks = new ArrayList(bnum);
    circles = new ArrayList(cnum);
    loadData();
    tb.ResetTitle();
  }
  
  public void draw()
  {
    background(0);
    if (mode == START_SCREEN)
    {
      tb.display();
    }
    if (mode == SHOP_SCREEN)
    {
      st.display();
    }
    if (mode == GAME_SCREEN || mode == GAMEOVER_SCREEN)
    {
      for (int i = 0; i < bnum; i++)
       {
         BlockCreate bc = (BlockCreate)blocks.get(i);
         bc.checkCancel();
         for (int j = 0; j < bc.blocknum; j++)
        {
          Block b = (Block)bc.lineBlocks.get(j);
          b.checkHit();
          b.display();
        }
       }
       for (int i = 0; i < bnum; i++)
       {
         BlockCreate bc = (BlockCreate)blocks.get(i);
         for (int j = 0; j < bc.toolnum; j++)
        {
          Tools t = (Tools)bc.lineTools.get(j);
          if (t.checkCollision())
          {
            if (t.type == 1)
            {
              TouchAddTime++;
              bc.toolnum--;
              bc.lineTools.remove(j);
            }
            else if (t.type == 2)
            {
              t.ToolHorizontal();
            }
            else if (t.type == 3)
            {
              t.ToolVertical();
            }
            else if (t.type == 4)
            {
              t.ToolDirection();
            }
            else if (t.type == 5)
            {
              money++;
              bc.toolnum--;
              bc.lineTools.remove(j);
            }
            else if (t.type == 6)
            {
              money += (int)random(0, 20);
              bc.toolnum--;
              bc.lineTools.remove(j);
            }
            else if (t.type == 7)
            {
              t.ToolBomb();
            }
            else if (t.type == 8)
            {
              t.ToolXray();
            }
            else if (t.type == 9)
            {
              t.ToolMini();
            }
            else if (t.type == 10)
            {
              CleanUp = false;
              bc.toolnum--;
              bc.lineTools.remove(j);
            }
            else if (t.type == 11)
            {
              t.ToolShow();
            }
            else if (t.type == 12)
            {
              AllTool = true;
              bc.toolnum--;
              bc.lineTools.remove(j);
            }
            else if (t.type == 13)
            {
              RoadNow = true;
              bc.toolnum--;
              bc.lineTools.remove(j);
            }
            else if (t.type == 14)
            {
              int hitRan = (int)random(100);
              if (hitRan >= 80)
              {
                t.ToolHit();
              }
            }
          }
          t.display();
          t.checkMini();
          t.HitDisplay();
         }
       }
       
       for (int i = 0; i < cnum; i++)
       {
          Circle c = (Circle)circles.get(i);
          if (c.TouchEdge())
          {
            if (firsttouch == -1)
            {
              firsttouch = i;
            }
          }
          c.movement();
          c.display(BallType);
       }
       
       
       //check Touching
       
       AllTouch = true;
      for (int i = 0; i < cnum; i++)
      {
      Circle c = (Circle)circles.get(i);
      if (c.isRunning)
        {
          AllTouch = false;
        }
      }
      
      checkRun();
      
      if (AllTouch && !RoundUp && !StartRun)
      {
         NUMBER ++;
         for (int i = 0; i < bnum ;i++)
          {
            BlockCreate bc = (BlockCreate)blocks.get(i);
            for (int j = 0; j < bc.blocknum; j++)
            {
              Block b = (Block)bc.lineBlocks.get(j);
              b.moveDown();
              if (b.posy + BlockSize >= EDGE)
              {
                isEnd = true;
              }
            }
            for (int j = 0; j < bc.toolnum; j++)
            {
              Tools t = (Tools)bc.lineTools.get(j);
              t.moveDown();
              if (t.CancelAfterRound)
              {
                bc.lineTools.remove(j);
                bc.toolnum--;
              }
              if (t.pos.y + BlockSize >= EDGE)
              {
                blocks.remove(i);
                bnum--;
              }
            }
          }
          //Clean up
          if (CleanUp)
          {
            for (int i = 0 ; i < cnum; i++)
            {
              Circle c1;
              c1 = (Circle)circles.get(firsttouch);
              float xx = c1.position.x;
              float yy = c1.position.y;
              if (i != firsttouch)
              {
                Circle c = (Circle)circles.get(i);
                c.setX(xx, yy);
              }
            }
          }
          //add Circle
          for (int i = 0; i < TouchAddTime; i++)
          {
            Circle c1;
            if (CleanUp)
            {
              c1 = (Circle)circles.get(firsttouch);
            }
            else
            
            {
              c1 = (Circle)circles.get(0);
            }
            float xx = c1.position.x;
            Circle c = new Circle(xx, EDGE, 10, CIR_DIAMETER);
            cnum++;
            circles.add(c);
          }
         TouchAddTime = 0;
         firsttouch = -1;
         BlockCreate bc = new BlockCreate(NUMBER, AllTool);
         if (AllTool)
         {
           AllTool = false;
         }
         blocks.add(bc);
         bnum++;
         RoundUp = true;
         
      }
      
      if (AllTouch && RoadNow)
      {
        //predict the road
          for (int i = 0; i < cnum; i++)
          {
            Circle c = (Circle)circles.get(i);
            c.Prediction(mouseX, mouseY);
          }
      }
      
      //after all checking
      //show the ball number that you have
      textSize(18);
      text("MONEY: " + Integer.toString(money), WIDTH - 100, HEIGHT - 60);
      text("BALL: " + Integer.toString(cnum), WIDTH - 100, HEIGHT - 40);
      
      if (mouseX >= 0 && mouseX <= 40 && mouseY <= HEIGHT && mouseY >= EDGE)
      {
        fill(0xff2EFFEC);
      }
      else
      {
        fill(255);
      }
      text("EXIT", 0, HEIGHT - 10);
      
      if (isEnd)
      {
        if (AddMoney)
        {
          money += NUMBER / 10;
          AddMoney = false;
        }
        GameOverShow();
        mode = GAMEOVER_SCREEN;
        if (cnum > highest)
        {
          highest = cnum;
        }
      }
    }
  }
  
  public void mousePressed()
     {
       if (mode == START_SCREEN)
       {
          if (mouseX >= 150 && mouseX <= 360 && mouseY >= 280 && mouseY <= 370)
          {
            mode = GAME_SCREEN;
            startGame();
          }
          if (mouseX >= 150 && mouseX <= 360 && mouseY >= 400 && mouseY <= 490)
          {
            mode = SHOP_SCREEN;
          }
          if (mouseX >= 150 && mouseX <= 360 && mouseY >= 520 && mouseY <= 610)
          {
            exit();
          }
       }
       if (mode == SHOP_SCREEN)
       {
         if (mouseX >= 150 && mouseX <= 360 && mouseY >= 550 && mouseY <= 640)
         {
           mode = START_SCREEN;
           tb.ResetTitle();
           saveData();
         }
         
         //choosing the store
         if (mouseX >= 50 && mouseX <= 200 && mouseY >= 230 && mouseY <= 250)
         {
           st.storemode = TOOL_UNLOCK;
         }
         if (mouseX >= 250 && mouseX <= 400 && mouseY >= 230 && mouseY <= 250)
         {
           st.storemode = SKIN_UNLOCK;
         }
         
         //unlocking the new item
         if (st.storemode == TOOL_UNLOCK)
         {
           if (mouseX >= 40 && mouseX <= 100 && mouseY >= 260 && mouseY <= 300 && !HoriUnlock)
           {
             if (money >= 15)
             {
               money -= 15;
               HoriUnlock = true;
             }
           }
           if (mouseX >= 140 && mouseX <= 200 && mouseY >= 260 && mouseY <= 300 && !VertiUnlock)
           {
             if (money >= 15)
             {
               money -= 15;
               VertiUnlock = true;
             }
           }
           if (mouseX >= 240 && mouseX <= 300 && mouseY >= 260 && mouseY <= 300 && !RandUnlock)
           {
             if (money >= 20)
             {
               money -= 20;
               RandUnlock = true;
             }
           }
           if (mouseX >= 340 && mouseX <= 400 && mouseY >= 260 && mouseY <= 300 && !SpecUnlock)
           {
             if (money >= 25)
             {
               money -= 25;
               SpecUnlock = true;
             }
           }
           if (mouseX >= 40 && mouseX <= 100 && mouseY >= 360 && mouseY <= 400 && !BombUnlock)
           {
             if (money >= 15)
             {
               money -= 15;
               BombUnlock = true;
             }
           }
           if (mouseX >= 140 && mouseX <= 200 && mouseY >= 360 && mouseY <= 400 && !XrayUnlock)
           {
             if (money >= 40)
             {
               money -= 40;
               XrayUnlock = true;
             }
           }
           if (mouseX >= 240 && mouseX <= 300 && mouseY >= 360 && mouseY <= 400 && !MiniUnlock)
           {
           
             if (money >= 40)
             {
               money -= 40;
               MiniUnlock = true;
             }
           }
           if (mouseX >= 340 && mouseX <= 400 && mouseY >= 360 && mouseY <= 400 && !CleanUnlock)
           {
             if (money >= 40)
             {
               money -= 40;
               CleanUnlock = true;
             }
           }
           if (mouseX >= 40 && mouseX <= 100 && mouseY >= 460 && mouseY <= 500 && !ShowUnlock)
           {
             if (money >= 50)
             {
               money -= 50;
               ShowUnlock = true;
             }
           }
           if (mouseX >= 140 && mouseX <= 200 && mouseY >= 460 && mouseY <= 500 && !ToolUnlock)
           {
             if (money >= 80)
             {
               money -= 80;
               ToolUnlock = true;
             }
           }
           if (mouseX >= 240 && mouseX <= 300 && mouseY >= 460 && mouseY <= 500 && !RoadUnlock)
           {
             if (money >= 30)
             {
               money -= 30;
               RoadUnlock = true;
             }
           }
           if (mouseX >= 340 && mouseX <= 400 && mouseY >= 460 && mouseY <= 500 && !HitBoxUnlock)
           {
             if (money >= 100)
             {
               money -= 100;
               HitBoxUnlock = true;
             }
           }
         }  
         else if (st.storemode == SKIN_UNLOCK)
         {
           if (mouseX >= 40 && mouseX <= 100 && mouseY >= 260 && mouseY <= 300 && !Skin1Unlock)
           {
             if (money >= 20)
             {
               money -= 20;
               Skin1Unlock = true;
             }
           }
           if (mouseX >= 140 && mouseX <= 200 && mouseY >= 260 && mouseY <= 300 && !Skin2Unlock)
           {
             if (money >= 20)
             {
               money -= 20;
               Skin2Unlock = true;
             }
           }
           if (mouseX >= 240 && mouseX <= 300 && mouseY >= 260 && mouseY <= 300 && !Skin3Unlock)
           {
             if (money >= 20)
             {
               money -= 20;
               Skin3Unlock = true;
             }
           }
           if (mouseX >= 340 && mouseX <= 400 && mouseY >= 260 && mouseY <= 300 && !Skin4Unlock)
           {
             if (money >= 30)
             {
               money -= 30;
               Skin4Unlock = true;
             }
           }
           if (mouseX >= 40 && mouseX <= 100 && mouseY >= 360 && mouseY <= 400 && !Skin5Unlock)  
           {
             if (money >= 50)
             {
               money -= 50;
               Skin5Unlock = true;
             }
           }
           if (mouseX >= 140 && mouseX <= 200 && mouseY >= 360 && mouseY <= 400 && !Skin6Unlock)
           {
             if (money >= 30)
             {
               money -= 30;
               Skin6Unlock = true;
             }
           }
           if (mouseX >= 240 && mouseX <= 300 && mouseY >= 360 && mouseY <= 400 && !Skin7Unlock)
           {
             if (money >= 60)
             {
               money -= 60;
               Skin7Unlock = true;
             }
           }
           if (mouseX >= 340 && mouseX <= 400 && mouseY >= 360 && mouseY <= 400 && !Skin8Unlock)
           {
             if (money >= 999)
             {
               money -= 999;
               Skin8Unlock = true;
             }
           }
         }
       }
       if (mode == GAME_SCREEN)
       {
         if (mouseY < EDGE)
         {
           if (AllTouch == true && isEnd == false)
           {
             StartRun = true;
             thatx = mouseX;
             thaty = mouseY;
             RoundUp = false;
             RoadNow = false;
             CleanUp = true;
           }
         }
         else if (mouseX >= 0 && mouseX <= 40 && mouseY <= HEIGHT && mouseY >= EDGE)
         {
           tb.ResetTitle();
           resetAllData();
           mode = START_SCREEN;
           resetAllData();
           saveData();
         }
       }
       if (mode == GAMEOVER_SCREEN)
       {
         mode = START_SCREEN;
         tb.ResetTitle();
         resetAllData();
         saveData();
       }
     }
       
  public void checkRun()
  {
    if (StartRun)
    {
      if (!CleanUp)
      {
        for (int i = 0; i < cnum; i++)
        {
          Circle c = (Circle)circles.get(i);
          c.Run(thatx, thaty);
        }
        CleanUp = true;
        StartRun = false;
        pointer = 0;
        counttime = 0;
        AllRun = true;
      }
      else
      {
        if (pointer < cnum)
        {
          if (pointer * 5 == counttime)
          {
            Circle c = (Circle)circles.get(pointer);
            c.Run(thatx, thaty);
            pointer++;
          }
          counttime++;
        }
        else
        {
        StartRun = false;
        pointer = 0;
        counttime = 0;
        AllRun = true;
        }
      }
    }
  }
  
  public void keyPressed()
  {
    if (key == '0')
    {
      BallType = 0;
    }
    if (key == '1' && Skin1Unlock)
    {
      BallType = 1;
    }
    if (key == '2' && Skin2Unlock)
    {
      BallType = 2;
    }
    if (key == '3' && Skin3Unlock)
    {
      BallType = 3;
    }
    if (key == '4' && Skin4Unlock)
    {
      BallType = 4;
    }
    if (key == '5' && Skin5Unlock)
    {
      BallType = 5;
    }
    if (key == '6' && Skin6Unlock)
    {
      BallType = 6;
    }
    if (key == '7' && Skin7Unlock)
    {
      BallType = 7;
    }
    if (key == '8' && Skin8Unlock)
    {
      BallType = 8;
    }
  }
    
  public void startGame()
  {
    ci = new Circle(random(0, WIDTH), EDGE, 10, CIR_DIAMETER);
    b = new BlockCreate(NUMBER, false);
    blocks.add(b);
    circles.add(ci);
    bnum++;
    cnum++;
  }
  
  public void resetAllData()
  {
    for (int i = 0; i < bnum; i++)
    {
      blocks.remove(i);
      bnum--;
      i--;
    }
    for (int i = 0; i < cnum; i++)
    {
      circles.remove(i);
      cnum--;
      i--;
    }
    cnum = 0;
    bnum = 0;
    firsttouch = -1;
    NUMBER = 1;
    isEnd = false;
    RoundUp = true;
    AllTouch = true;
    AllRun = false;
    StartRun = false;
    AllTool = false;
    AddMoney = true;
    pointer = 0;
    counttime = 0;
  }
  
  public void GameOverShow()
  {
    noStroke();
    fill(0xffD80D0D);
    rect(WIDTH/2 - 140, HEIGHT/2 - 40, 300, 100);
    fill(0xff0DD8AE);
    rect(WIDTH/2 - 150, HEIGHT/2 - 50, 300, 100);
    fill(0xff0300FA);
    textSize(16);
    text("GameOver!", WIDTH/2 - 100, HEIGHT/2);
    text("Score: " + cnum, WIDTH/2 - 100, HEIGHT/2 + 20);
  }
  
  public void saveData()
  {
    save = createWriter("save.txt");
    save.print(money+"S");
    save.print(highest+"S");
    save.print(HoriUnlock+"S");
    save.print(VertiUnlock+"S");
    save.print(RandUnlock+"S");
    save.print(SpecUnlock+"S");
    save.print(BombUnlock+"S");
    save.print(XrayUnlock+"S");
    save.print(MiniUnlock+"S");
    save.print(CleanUnlock+"S");
    save.print(ShowUnlock+"S");
    save.print(ToolUnlock+"S");
    save.print(RoadUnlock+"S");
    save.print(HitBoxUnlock+"S");
    save.print(Skin1Unlock+"S");
    save.print(Skin2Unlock+"S");
    save.print(Skin3Unlock+"S");
    save.print(Skin4Unlock+"S");
    save.print(Skin5Unlock+"S");
    save.print(Skin6Unlock+"S");
    save.print(Skin7Unlock+"S");
    save.print(Skin8Unlock+"S");
    save.flush();
    save.close();
  }
  
  public void loadData()
  {
    String line;
    try
    {
    reader = createReader("save.txt");
    try
      {
        line = reader.readLine();  
      } catch (IOException e)
      {
        e.printStackTrace();
        line = null;
      }
      if (line == null)
      {
        noLoop();
      }
      else
      {
        String[] pieces = split(line, 'S');
        money = PApplet.parseInt(pieces[0]);
        highest = PApplet.parseInt(pieces[1]);
        HoriUnlock = PApplet.parseBoolean(pieces[2]);
        VertiUnlock = PApplet.parseBoolean(pieces[3]);
        RandUnlock = PApplet.parseBoolean(pieces[4]);
        SpecUnlock = PApplet.parseBoolean(pieces[5]);
        BombUnlock = PApplet.parseBoolean(pieces[6]);
        XrayUnlock = PApplet.parseBoolean(pieces[7]);
        MiniUnlock = PApplet.parseBoolean(pieces[8]);
        CleanUnlock = PApplet.parseBoolean(pieces[9]);
        ShowUnlock = PApplet.parseBoolean(pieces[10]);
        ToolUnlock = PApplet.parseBoolean(pieces[11]);
        RoadUnlock = PApplet.parseBoolean(pieces[12]);
        HitBoxUnlock = PApplet.parseBoolean(pieces[13]);
        Skin1Unlock = PApplet.parseBoolean(pieces[14]);
        Skin2Unlock = PApplet.parseBoolean(pieces[15]);
        Skin3Unlock = PApplet.parseBoolean(pieces[16]);
        Skin4Unlock = PApplet.parseBoolean(pieces[17]);
        Skin5Unlock = PApplet.parseBoolean(pieces[18]);
        Skin6Unlock = PApplet.parseBoolean(pieces[19]);
        Skin7Unlock = PApplet.parseBoolean(pieces[20]);
        Skin8Unlock = PApplet.parseBoolean(pieces[21]);
      }
    }catch(Exception e){}     
  }
  
final int BlockSize = 50;
class Block
{
  float posx, posy;
  int number;
  boolean showHighBlock = false;
  public Block(float x, float y, int number)
  {
    posx = x;
    posy = y;
    this.number = number;
  }
  
  public void moveDown()
  {
    posy += BlockSize;
  }
  
  public void getHit()
  {
    number--;
  }
  
  public void checkHit()
  {
    for (int i = 0; i < cnum; i++)
    {
      Circle c = (Circle)circles.get(i);
      if (c.CollisionWithBlock(posx, posy))
      {
        number--;
      }
    }
  }
  
  public void display()
  {
    strokeWeight(3);

    if (number <= 5)
    {
      stroke(0xffFC0000);
      fill(0);
      rect(posx, posy, BlockSize, BlockSize);
      fill(0xffFC0000);
    }
    else if (number <= 10)
    {
      stroke(0xffFF9A03);
      fill(0);
      rect(posx, posy, BlockSize, BlockSize);
      fill(0xffFF9A03);
    }
    else if (number <= 20)
    {
      stroke(0xff23FF03);
      fill(0);
      rect(posx, posy, BlockSize, BlockSize);
      fill(0xff23FF03);
    }
    else if (number <= 30)
    {
      stroke(0xff03FF76);
      fill(0);
      rect(posx, posy, BlockSize, BlockSize);
      fill(0xff03FF76);
    }
    else if (number <= 40)
    {
      stroke(0xff03FFF0);
      fill(0);
      rect(posx, posy, BlockSize, BlockSize);
      fill(0xff03FFF0);
    }
    else if (number <= 50)
    {
      stroke(0xff0354FF);
      fill(0);
      rect(posx, posy, BlockSize, BlockSize);
      fill(0xff0354FF);
    }
    else if (number <= 60)
    {
      stroke(0xffFA03FF);
      fill(0);
      rect(posx, posy, BlockSize, BlockSize);
      fill(0xffFA03FF);
    }
    else
    {
      if (!showHighBlock)
      {
        stroke( 50 + (number - 60) * 5);
        fill(0);
        rect(posx, posy, BlockSize, BlockSize);
        fill( 50 + (number - 60) * 5);
      }
      else
      {
        int ranr = (int)random(225);
        int ranb = (int)random(225);
        int rang = (int)random(225);
        stroke(ranr, ranb, rang);
        fill(0);
        rect(posx, posy, BlockSize, BlockSize);
        fill(ranr, ranb, rang);
        showHighBlock = false;
      }
    }
    
    textSize(32);
    text( Integer.toString(number), posx + BlockSize/4, posy + BlockSize/3 * 2);
  }
}
class BlockCreate
{   
  ArrayList lineBlocks;
  ArrayList lineTools;
  int blocknum = 0;
  int toolnum = 0;
  int number;
  
  boolean hasAdd = false;
  boolean hasHorizontal = false;
  boolean hasVertical = false;
  boolean hasDirection = false;
  boolean hasSpecialDollar = false;
  boolean hasBomb = false;
  boolean hasXray = false;
  boolean hasMini = false;
  boolean hasClean = false;
  boolean hasShow = false;
  boolean hasTool = false;
  boolean hasRoad = false;
  boolean hasHit = false;
  
  public BlockCreate(int number, boolean setTool)
  {
    lineTools = new ArrayList(toolnum);
    lineBlocks = new ArrayList(blocknum);
    this.number = number;
    if (setTool)
    {
      CreateToolLine();
    }
    else
    {
      CreateLine();
    }
  }
  
  public void CreateLine()
  {
    float pointerx = 0;
    float pointery = BlockSize;
    while (pointerx + BlockSize < WIDTH)
    {
      int ran = (int)(random(1,100));
      //60% to be a block and 40% to be a tool
      if (ran <= 50 + NUMBER/5)
      {
        int ran2 = (int)(random(1, 100));
        Block b;
        if (ran <= 80 - NUMBER/5)
        {
          b = new Block(pointerx, pointery, number);
        }
        else
        {
          b = new Block(pointerx, pointery, number*2);
        }
        blocknum++;
        lineBlocks.add(b);
      }
      else if (ran <= 60 + NUMBER/5)
      {
        //DOING NOTHING
      }
      else
      {
        int ran2 = (int)(random(1, 100));
        if (ran2 <= 30 && !hasAdd)
        {
          Tools t = new Tools(pointerx, pointery, 1);
          toolnum++;
          lineTools.add(t);
          hasAdd = true;
        }
        else if (ran2 >= 30 && ran2 <= 35 && !hasXray && XrayUnlock)
        {
          Tools t = new Tools(pointerx, pointery, 8);
          toolnum++;
          lineTools.add(t);
          hasXray = true;
        }
        else if (ran2 >= 36 && ran2 <= 37 && !hasSpecialDollar && SpecUnlock)
        {
          Tools t = new Tools(pointerx, pointery, 6);
          toolnum++;
          lineTools.add(t);
          hasSpecialDollar = true;
        }
        else if (ran2 >= 38 && ran2 <= 42)
        {
          Tools t = new Tools(pointerx, pointery, 5);
          toolnum++;
          lineTools.add(t);
        }
        else if (ran2 >= 43 && ran2 <= 47 && !hasBomb && BombUnlock)
        {
          Tools t = new Tools(pointerx, pointery, 7);
          toolnum++;
          lineTools.add(t);
          hasBomb = true;
        }
        else if (ran2 >= 48 && ran2 <= 52 && !hasHorizontal && HoriUnlock)
        {
          Tools t = new Tools(pointerx, pointery, 2);
          toolnum++;
          lineTools.add(t);
          hasHorizontal = true;
        }
        else if (ran2 >= 53 && ran2 <= 57 && !hasVertical && VertiUnlock)
        {
          Tools t = new Tools(pointerx, pointery, 3);
          toolnum++;
          lineTools.add(t);
          hasVertical = true;
        }
        else if (ran2 >= 58 && ran2 <= 62 && !hasDirection && RandUnlock)
        {
          Tools t = new Tools(pointerx, pointery, 4);
          toolnum++;
          lineTools.add(t);
          hasDirection = true;
        }
        else if (ran2 >= 63 && ran2 <= 65 && !hasMini && MiniUnlock)
        {
          Tools t = new Tools(pointerx, pointery, 9);
          toolnum++;
          lineTools.add(t);
          hasMini = true;
        }
        else if (ran2 >= 66 && ran2 <= 71 && !hasClean && CleanUnlock)
        {
          Tools t = new Tools(pointerx, pointery, 10);
          toolnum++;
          lineTools.add(t);
          hasClean = true;
        }
        else if (ran2 >= 72 && ran2 >= 80 && !hasShow && ShowUnlock && NUMBER >= 30)
        {
          Tools t = new Tools(pointerx, pointery, 11);
          toolnum++;
          lineTools.add(t);
          hasShow = true;
        }
        else if (ran2 >= 81 && ran2 <= 86 && !hasRoad && RoadUnlock)
        {
          Tools t = new Tools(pointerx, pointery, 13);
          toolnum++;
          lineTools.add(t);
          hasRoad = true;
        }
        else if (ran2 >= 87 && ran2 <= 88 && !hasHit && HitBoxUnlock)
        {
          Tools t = new Tools(pointerx, pointery, 14);
          toolnum++;
          lineTools.add(t);
          hasHit = true;
        }
        else if (ran2 == 99 && !hasTool && ToolUnlock)
        {
          Tools t = new Tools(pointerx, pointery, 12);
          toolnum++;
          lineTools.add(t);
          hasTool = true;
        }
      }
      pointerx += BlockSize;
    }
    if (!hasAdd)
    {
      Tools t = new Tools(pointerx, pointery, 1);
      toolnum++;
      lineTools.add(t);
      hasAdd = true;
    }
    else 
    {
      int ran = (int)(random(1, 10));
      if (ran >= 6)
      {
        int ran2 = (int)(random(1, 10));
        Block b;
        if (ran2 >= 8)
        {
          b = new Block(pointerx, pointery, number);
        }
        else
        {
          b = new Block(pointerx, pointery, number*2);
        }
        blocknum++;
        lineBlocks.add(b);
      }
    }
  }
  
  public void CreateToolLine()
  {
    float pointerx = 0;
    float pointery = BlockSize;
    while (pointerx + BlockSize < WIDTH)
    {
      int ran = (int)random(100);
      if (ran <= 30 && !hasAdd)
      {
        Tools t = new Tools(pointerx, pointery, 1);
        toolnum++;
        lineTools.add(t);
        hasAdd = true;
      }
      else if (ran >= 31 && ran <= 35 && !hasXray && XrayUnlock)
      {
        Tools t = new Tools(pointerx, pointery, 8);
        toolnum++;
        lineTools.add(t);
        hasXray = true;
      }
      else if (ran >= 36 && ran <= 37 && !hasSpecialDollar && SpecUnlock)
      {
        Tools t = new Tools(pointerx, pointery, 6);
        toolnum++;
        lineTools.add(t);
        hasSpecialDollar = true;
      }
      else if (ran >= 38 && ran <= 42)
      {
        Tools t = new Tools(pointerx, pointery, 5);
        toolnum++;
        lineTools.add(t);
      }
      else if (ran >= 43 && ran <= 47 && !hasBomb && BombUnlock)
      {
        Tools t = new Tools(pointerx, pointery, 7);
        toolnum++;
        lineTools.add(t);
        hasBomb = true;
      }
      else if (ran >= 48 && ran <= 52 && !hasHorizontal && HoriUnlock)
      {
        Tools t = new Tools(pointerx, pointery, 2);
        toolnum++;
        lineTools.add(t);
        hasHorizontal = true;
      }
      else if (ran >= 53 && ran <= 57 && !hasVertical && VertiUnlock)
      {
        Tools t = new Tools(pointerx, pointery, 3);
        toolnum++;
        lineTools.add(t);
        hasVertical = true;
      }
      else if (ran >= 58 && ran <= 62 && !hasDirection && RandUnlock)
      {
        Tools t = new Tools(pointerx, pointery, 4);
        toolnum++;
        lineTools.add(t);
        hasDirection = true;
      }
      else if (ran >= 63 && ran <= 65 && !hasMini && MiniUnlock)
      {
        Tools t = new Tools(pointerx, pointery, 9);
        toolnum++;
        lineTools.add(t);
        hasMini = true;
      }
      else if (ran >= 66 && ran <= 71 && !hasClean && CleanUnlock)
      {
        Tools t = new Tools(pointerx, pointery, 10);
        toolnum++;
        lineTools.add(t);
        hasClean = true;
      }
      else if (ran >= 72 && ran <= 80 && !hasShow && ShowUnlock && NUMBER >= 30)
      {
        Tools t = new Tools(pointerx, pointery, 11);
        toolnum++;
        lineTools.add(t);
        hasShow = true;
      }
      else if (ran >= 81 && ran <= 86 && !hasRoad && RoadUnlock)
      {
        Tools t = new Tools(pointerx, pointery, 13);
        toolnum++;
        lineTools.add(t);
        hasRoad = true;
      }
      pointerx += BlockSize;
    }
    if (!hasAdd)
    {
      Tools t = new Tools(pointerx, pointery, 1);
      toolnum++;
      lineTools.add(t);
      hasAdd = true;
    }
  }
  
  public void checkCancel()
  {
    for (int i = 0; i < blocknum; i++)
    {
      Block b = (Block)lineBlocks.get(i);
      if (b.number <= 0)
      {
        lineBlocks.remove(i);
        blocknum--;
      }
    }
  }
}
final int EDGE = HEIGHT - 30;

class Circle{
  PVector position;
  PVector speed;
  float constants;
  int diameter;
  int StopTime = 0;
  boolean isRunning = false;
  int shootTime = 0;
  PImage dogdog = loadImage("doge.png");
  
  int showTime = 0;
  CircleRoad cr;
  
  public Circle(float x, float y, float s, int d)
  {
    position = new PVector(x, y);
    constants = s;
    speed = new PVector(constants, constants);
    diameter = d;
    cr = new CircleRoad(x, y, constants, constants);
  }
  
  public void movement()
  {
    if (isRunning)
    {
      adjustSpeed();
      StopTime--;
    }
    position.add(speed);
    
  }
  
  public void setX(float x, float y)
  {
    position.x = x;
    position.y = y;
  }
  
  public void checkOutOfBound()
  {
    if (position.x >= WIDTH-1 || position.x <= 1)
    {
      Collision('x');
    }
    if (position.y >= HEIGHT-1 || position.y <= 1)
    {
      Collision('y');
    }
  }
  
  public boolean CollisionWithBlock(float bposx, float bposy, float bwidth, float bheight)
  {
      boolean isCollision = false;
      float ptx = position.x;
      float pty = position.y;
      
      if (ptx > bposx + bwidth) {ptx = bposx + bwidth;}
      if (ptx < bposx) {ptx = bposx;}
      if (pty > bposy + bheight) {pty = bposy + bheight;}
      if (pty < bposy) {pty = bposy;}
      
      float dx = ptx - position.x;
      float dy = pty - position.y;
      float minidis = sqrt(dx*dx + dy*dy);
      if (minidis < diameter) {
        isCollision = true;
        if (position.x > bposx && position.x < bposx + bwidth)
        {
          Collision('y');
        }
        else if (position.y > bposy && position.y < bposy + bheight)
        {
          Collision('x');
        }
        else
        {
          Collision('x');
          Collision('y'); 
        }
      }
           
      return isCollision;
  }
  
  public boolean CollisionWithBlock(float bposx, float bposy)
  {
      boolean isCollision = false;
      float ptx = position.x;
      float pty = position.y;
      
      if (ptx > bposx + BlockSize) {ptx = bposx + BlockSize;}
      if (ptx < bposx) {ptx = bposx;}
      if (pty > bposy + BlockSize) {pty = bposy + BlockSize;}
      if (pty < bposy) {pty = bposy;}
      
      float dx = ptx - position.x;
      float dy = pty - position.y;
      float minidis = sqrt(dx*dx + dy*dy);
      if (minidis < diameter) {
        isCollision = true;
        if (position.x > bposx && position.x < bposx + BlockSize)
        {
          Collision('y');
        }
        else if (position.y > bposy && position.y < bposy + BlockSize)
        {
          Collision('x');
        }
        else
        {
          Collision('x');
          Collision('y'); 
        }
      }
           
      return isCollision;
  }
  
  public void Collision(char xy)
  {
    if (xy == 'x')
    {
      speed.x = -speed.x;
    }
    else if (xy == 'y')
    {
      speed.y = -speed.y;
    }
    else if (xy == 'b')
    {
      float tempspeed = speed.x;
      speed.x = speed.y;
      speed.y = tempspeed;
    }
  }
  
  public boolean TouchEdge()
  {
    if (StopTime <= 0)
    {
      if (position.y >= EDGE)
      {
        speed.x = 0;
        speed.y = 0;
        StopTime = 5;
        isRunning = false;
        return true;
      }
      return false;
    }
    return false;
  }
  
  public void Run(float mx, float my)
  {
    float holdsx = ((mx - position.x))/200;
    float holdsy = ((my - position.y))/200; 
    speed.x = holdsx;
    speed.y = holdsy;
    isRunning = true;
  }
  
  public void Prediction(float mx, float my)
  {
    float holdsx = (mx - position.x)/200;
    float holdsy = (my - position.y)/200;
    cr.update(position.x, position.y, holdsx, holdsy);
    cr.display();    
  }
  
  public void RandomDirection()
  {
    int ranx = (int)random(-WIDTH, WIDTH);
    int rany = (int)random(-HEIGHT, HEIGHT);
    float holdsx = ((ranx - position.x)*constants)/200;
    float holdsy = ((rany - position.y)*constants)/200; 
    speed.x = holdsx;
    speed.y = holdsy;
  }
  
  public void adjustSpeed()
  {
    float norm = sqrt(speed.x*speed.x + speed.y*speed.y);
    speed.x = (speed.x / norm) * constants;
    speed.y = (speed.y / norm) * constants;
  }
  
  public void star(float x, float y, float radius1, float radius2, int npoints)
  {
    float angle = TWO_PI / npoints;
    float halfAngle = angle/2.0f;
    beginShape();
    for(float a = 0; a < TWO_PI; a += angle)
    {
      float sx = x + cos(a) * radius2;
      float sy = y + sin(a) * radius2;
      vertex(sx, sy);
      sx = x + cos(a + halfAngle) * radius1;
      sy = y + sin(a + halfAngle) * radius1;
      vertex(sx, sy);
    }
    endShape(CLOSE);
  }
  
  public void display(int bt)
  {
    noStroke();
    checkOutOfBound();
    
    if (bt == 0)
    {
      fill(255);
      ellipse(position.x, position.y, diameter, diameter);
    }
    else if (bt == 1)
    {
      fill(0xffFC1919);
      stroke(0xffECFF21);
      strokeWeight(3);
      ellipse(position.x, position.y, diameter-1, diameter-1);
    }
    else if (bt == 2)
    {
      fill(0);
      stroke(255);
      strokeWeight(2);
      ellipse(position.x, position.y, diameter-1, diameter-1);
    }
    else if (bt == 3)
    {
      fill(0xffFF21E6);
      ellipse(position.x, position.y, diameter, diameter);
      fill(0xff3E21FF);
      ellipse(position.x, position.y, diameter-2, diameter-2);
      fill(0xff21FFE0);
      ellipse(position.x, position.y, diameter-4, diameter-4);
      fill(0xff2BFF21);
      ellipse(position.x, position.y, diameter-6, diameter-6);
      fill(0xffECFF21);
      ellipse(position.x, position.y, diameter-8, diameter-8);
      fill(0xffFF2121);
      ellipse(position.x, position.y, diameter-10, diameter-10);
    }
    else if (bt == 4)
    {
      pushMatrix();
      translate(position.x, position.y);
      rotate(showTime/10.f);
      fill(255);
      ellipse(0, 0, diameter, diameter);
      stroke(0);
      strokeWeight(3);
      line(-diameter, 0, diameter, 0);
      line(0, -diameter, 0, diameter);
      popMatrix();
    }
    else if (bt == 5)
    {
      pushMatrix();
      translate(position.x, position.y);
      rotate(showTime/10.f);
      fill(255);
      stroke(0xffF7F5DC);
      strokeWeight(3);
      ellipse(0, 0, diameter, diameter);
      noStroke();
      
      ellipse(diameter + 3, 0, 5, 5);
      ellipse(- diameter - 3, 0, 5, 5);
      ellipse(0, diameter + 3, 5, 5);
      ellipse(0, - diameter - 3, 5, 5);
      popMatrix();
    }
    else if (bt == 6)
    {
      pushMatrix();
      translate(position.x, position.y);
      rotate(showTime/30.f);
      noStroke();
      fill(0xffFFC455);
      ellipse(0, 0, diameter, diameter);
      fill(0xffFF0505);
      star(0, 0, ToolSize/4, 3, 5);
      popMatrix();
    }
    else if (bt == 7)
    {
      noStroke();
      pushMatrix();
      translate(position.x, position.y);
      rotate(showTime/30.f);
      fill(220, 46, 229, 80);
      ellipse(0, 0, diameter + 5, diameter + 5);
      fill(0xffDC2EE5);
      ellipse(0, 0, diameter, diameter);
      stroke(0xff55F2FF);
      strokeWeight(1);
      
      if (showTime % 10 != 0 && showTime % 9 != 0 && (showTime + 1) % 10 != 0)
      {
        line(0, 0, 1, 2);     
        line(1, 2, -1, 3);
        line(-1, 3, 0, diameter/2);
        
        line(0, 0, 1, -2);
        line(1, -2, 3, -1);
        line(3, -1, 7, -4);
        
        line(0, 0, -2, -1);
        line(-2, -1, -1, -3);
        line(-1, -3, -7, -4);
        
        fill(0xff55F2FF);
        noStroke();
        ellipse(0, diameter/2 + 5, 4, 4);
        ellipse((diameter/2+5)*cos(45) + 5, -(diameter/2 + 5)*cos(45)  , 4, 4);
        ellipse(-(diameter/2+5)*cos(45) - 5, -(diameter/2+5)*cos(45) , 4, 4);
      }
      popMatrix();
    }
    else if (bt == 8)
    {
      image(dogdog, position.x, position.y);
      
    }
    
    showTime++;
    //draw the edge
    if (mode == GAME_SCREEN)
    {
      fill(255);
      stroke(255, 0, 0);
      strokeWeight(3);
      line(0, EDGE, WIDTH, EDGE);
    }
  }
}
class CircleRoad
{
  PVector pos;
  PVector spe;
  
  float[] positionsx = new float[20];
  float[] positionsy = new float[20];
  int[] colors = new int[20];
  
  public CircleRoad(float x, float y, float vx, float vy)
  {
    pos = new PVector(x, y);
    spe = new PVector(vx, vy);
  }

  public void update(float x, float y, float vx, float vy)
  {
    pos.x = x;
    pos.y = y;
    spe.x = vx;
    spe.y = vy;
    int tempc = 225;
    float tempx = pos.x;
    float tempy = pos.y;
    adjustSpeed();
    for(int i = 0; i < 20; i++)
    {
      tempx += spe.x;
      tempy += spe.y;
      tempc -= 10;
      if (tempx >= WIDTH)
      {
        tempx = WIDTH - (tempx - WIDTH);
        spe.x = -spe.x;
      }
      else if (tempx <= 0)
      {
        tempx = -tempx;
        spe.x = -spe.x;
      }
      positionsx[i] = tempx;
      positionsy[i] = tempy;
      colors[i] = tempc;
    }
  }
  
  public void adjustSpeed()
  {
    float norm = sqrt(spe.x*spe.x + spe.y*spe.y);
    spe.x = (spe.x / norm) * 50;
    spe.y = (spe.y / norm) * 50;
  }
  
  public void display()
  {
    for (int i = 0; i < 20; i++)
    {
      fill(255, 255, 255, colors[i]);
      noStroke();
      ellipse(positionsx[i], positionsy[i], CIR_DIAMETER, CIR_DIAMETER);
    }
  }
}
class MiniBall
{
  int MINI_SIZE = 8;
  PVector pos;
  PVector speed;
  int constants;
  public MiniBall(float x, float y, int s, float sx, float sy)
  {
    pos = new PVector(x, y);
    constants = s;
    speed = new PVector(sx, sy);
  }
  
  public void movement()
  {
    adjustSpeed(); 
    pos.x += speed.x;
    pos.y += speed.y;
  }
  
  public void adjustSpeed()
  {
    float norm = sqrt(speed.x*speed.x + speed.y*speed.y);
    speed.x = (speed.x / norm) * constants;
    speed.y = (speed.y / norm) * constants;
  }
  
  public boolean checkDisappear()
  {
    if (pos.y >= EDGE)
    {
      return true;
    }
    if (pos.x >= WIDTH || pos.x <= 0)
    {
      return true;
    }
    if (pos.y >= HEIGHT || pos.y <= 0)
    {
      return true;
    }
    return false;
  }
  
  public boolean CollisionWithBlock(float bposx, float bposy)
  {
      boolean isCollision = false;
      float ptx = pos.x;
      float pty = pos.y;
      
      if (ptx > bposx + BlockSize) {ptx = bposx + BlockSize;}
      if (ptx < bposx) {ptx = bposx;}
      if (pty > bposy + BlockSize) {pty = bposy + BlockSize;}
      if (pty < bposy) {pty = bposy;}
      
      float dx = ptx - pos.x;
      float dy = pty - pos.y;
      float minidis = sqrt(dx*dx + dy*dy);
      if (minidis < MINI_SIZE) {
        isCollision = true;
      }          
      return isCollision;
  }
  
  public void display()
  {
    fill(0xffEEFF0D);
    noStroke();
    ellipse(pos.x, pos.y, MINI_SIZE, MINI_SIZE);
  }
}
boolean HoriUnlock = false;
boolean VertiUnlock = false;
boolean RandUnlock = false;
boolean SpecUnlock = false;
boolean BombUnlock = false;
boolean XrayUnlock = false;
boolean MiniUnlock = false;
boolean CleanUnlock = false;
boolean ShowUnlock = false;
boolean ToolUnlock = false;
boolean RoadUnlock = false;
boolean HitBoxUnlock = false;

boolean Skin1Unlock = false;
boolean Skin2Unlock = false;
boolean Skin3Unlock = false;
boolean Skin4Unlock = false;
boolean Skin5Unlock = false;
boolean Skin6Unlock = false;
boolean Skin7Unlock = false;
boolean Skin8Unlock = false;

final int TOOL_UNLOCK = 1222;
final int SKIN_UNLOCK = 2333;

class Store
{
  boolean onExit = false;
  boolean onHow = false;
  int storemode = TOOL_UNLOCK;
  Circle c4 = new Circle(375, 325, 0, CIR_DIAMETER);
  Circle c5 = new Circle(75, 425, 0, CIR_DIAMETER);
  Circle c6 = new Circle(175, 425, 0, CIR_DIAMETER);
  Circle c7 = new Circle(275, 425, 0, CIR_DIAMETER);
  Circle c8 = new Circle(375, 425, 0, CIR_DIAMETER);
  
  public void CheckTouch()
  {
    if (mouseX >= 150 && mouseX <= 360 && mouseY >= 550 && mouseY <= 640)
    {
      onExit = true;
    }  
    else
    {
      onExit = false;
    }
    
    //choosing tool or skin
    if (mouseX >= 50 && mouseX <= 200 && mouseY >= 230 && mouseY <= 250)
    {
        fill(0xff02F5DE);
        text("~TOOLS UNLOCK~", 50, 250);
    }
    if (mouseX >= 250 && mouseX <= 400 && mouseY >= 230 && mouseY <= 250)
    {
        fill(0xff02F5DE);
        text("~SKINS UNLOCK~", 250, 250);
    }
    
    
    if (storemode == TOOL_UNLOCK)
    {
      if (mouseX >= 40 && mouseX <= 100 && mouseY >= 260 && mouseY <= 300 && !HoriUnlock)
      {
        textSize(25);
        text("BUY?", 80, 320);
      }
      if (mouseX >= 140 && mouseX <= 200 && mouseY >= 260 && mouseY <= 300 && !VertiUnlock)
      {
        textSize(25);
        text("BUY?", 180, 320);
      }
      if (mouseX >= 240 && mouseX <= 300 && mouseY >= 260 && mouseY <= 300 && !RandUnlock)
      {
        textSize(25);
        text("BUY?", 280, 320);
      }
      if (mouseX >= 340 && mouseX <= 400 && mouseY >= 260 && mouseY <= 300 && !SpecUnlock)
      {
        textSize(25);
        text("BUY?", 380, 320);
      }
      if (mouseX >= 40 && mouseX <= 100 && mouseY >= 360 && mouseY <= 400 && !BombUnlock)
      {
        textSize(25);
        text("BUY?", 80, 420);
      }
      if (mouseX >= 140 && mouseX <= 200 && mouseY >= 360 && mouseY <= 400 && !XrayUnlock)
      {
        textSize(25);
        text("BUY?", 180, 420);
      }
      if (mouseX >= 240 && mouseX <= 300 && mouseY >= 360 && mouseY <= 400 && !MiniUnlock)
      {
        textSize(25);
        text("BUY?", 280, 420);
      }
      if (mouseX >= 340 && mouseX <= 400 && mouseY >= 360 && mouseY <= 400 && !CleanUnlock)
      {
        textSize(25);
        text("BUY?", 380, 420);
      }
      if (mouseX >= 40 && mouseX <= 100 && mouseY >= 460 && mouseY <= 500 && !ShowUnlock)
      {
        textSize(25);
        text("BUY?", 80, 520);
      }
      if (mouseX >= 140 && mouseX <= 200 && mouseY >= 460 && mouseY <= 500 && !ToolUnlock)
      {
        textSize(25);
        text("BUY?", 180, 520);
      }
      if (mouseX >= 240 && mouseX <= 300 && mouseY >= 460 && mouseY <= 500 && !RoadUnlock)
      {
        textSize(25);
        text("BUY?", 280, 520);
      }
      if (mouseX >= 340 && mouseX <= 400 && mouseY >= 460 && mouseY <= 500 && !HitBoxUnlock)
      {
        textSize(25);
        text("BUY?", 380, 520);
      }
    }
    else if (storemode == SKIN_UNLOCK)
    {
      if (mouseX >= 40 && mouseX <= 100 && mouseY >= 260 && mouseY <= 300 && !Skin1Unlock)
      {
        textSize(25);
        text("BUY?", 80, 320);
      }
      if (mouseX >= 140 && mouseX <= 200 && mouseY >= 260 && mouseY <= 300 && !Skin2Unlock)
      {
        textSize(25);
        text("BUY?", 180, 320);
      }
      if (mouseX >= 240 && mouseX <= 300 && mouseY >= 260 && mouseY <= 300 && !Skin3Unlock)
      {
        textSize(25);
        text("BUY?", 280, 320);
      }
      if (mouseX >= 340 && mouseX <= 400 && mouseY >= 260 && mouseY <= 300 && !Skin4Unlock)
      {
        textSize(25);
        text("BUY?", 380, 320);
      }
      if (mouseX >= 40 && mouseX <= 100 && mouseY >= 360 && mouseY <= 400 && !Skin5Unlock)
      {
        textSize(25);
        text("BUY?", 80, 420);
      }
      if (mouseX >= 140 && mouseX <= 200 && mouseY >= 360 && mouseY <= 400 && !Skin6Unlock)
      {
        textSize(25);
        text("BUY?", 180, 420);
      }
      if (mouseX >= 240 && mouseX <= 300 && mouseY >= 360 && mouseY <= 400 && !Skin7Unlock)
      {
        textSize(25);
        text("BUY?", 280, 420);
      }
      if (mouseX >= 340 && mouseX <= 400 && mouseY >= 360 && mouseY <= 400 && !Skin8Unlock)
      {
        textSize(25);
        text("BUY?", 380, 420);
      }
      
      if (mouseX >= 40 && mouseX <= 280 && mouseY >= 460 && mouseY <= 500)
      {
        onHow = true;
      }
      else 
      {
        onHow = false;
      }
    }
  }
  
  public void display()
  {
    noStroke();
    fill(0xff1BF0D5);
    rect(110, 80, 300, 100);
    fill(0xff2B1BF0);
    rect(100, 70, 300, 100);
    fill(0xffF01BE5);  
    textSize(50);
    text("STORE", 180, 140);

    if (!onExit)
    {
      fill(0xff0208F5);
      rect(160, 560, 200, 80);
      fill(0xff02F5DE);
      rect(150, 550, 200, 80);
      textSize(30);
      fill(0xff0208F5);
      text("EXIT", 220, 600);
    }
    else
    {
      fill(0xff02F5DE);
      rect(160, 560, 200, 80);
      fill(0xff0208F5);
      rect(150, 550, 200, 80);
      textSize(30);
      fill(0xff02F5DE);
      text("EXIT", 220, 600);
    }
    
    if (storemode == TOOL_UNLOCK)
    {
      //price here
      fill(0xff02F5DE);
      text("$15", 50, 300);
      text("$15", 150, 300);
      text("$20", 250, 300);
      text("$25", 350, 300);
      text("$15", 50, 400);
      text("$40", 150, 400);
      text("$40", 250, 400);
      text("$40", 350, 400);
      text("$50", 50, 500);
      text("$80", 150, 500);
      text("$30", 250, 500);
      text("$100", 350, 500);
      
      if (HoriUnlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(45, 290, 100, 290);
        Tools t1 = new Tools(50, 300, 2);
        t1.display();
      }
      if (VertiUnlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(145, 290, 200, 290);
        Tools t2 = new Tools(150, 300, 3);
        t2.display();
      }
      if (RandUnlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(245, 290, 300, 290);
        Tools t3 = new Tools(250, 300, 4);
        t3.display();
      }
      if (SpecUnlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(345, 290, 400, 290);
        Tools t4 = new Tools(350, 300, 6);
        t4.display();
      }
      if (BombUnlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(45, 390, 100, 390);
        Tools t5 = new Tools(50, 400, 7);
        t5.display();
      }
      if (XrayUnlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(145, 390, 200, 390);
        Tools t6 = new Tools(150, 400, 8);
        t6.display();
      }
      if (MiniUnlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(245, 390, 300, 390);
        Tools t7 = new Tools(250, 400, 9);
        t7.display();
      }
      if (CleanUnlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(345, 390, 400, 390);
        Tools t8 = new Tools(350, 400, 10);
        t8.display();
      }
      if (ShowUnlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(45, 490, 100, 490);
        Tools t9 = new Tools(50, 500, 11);
        t9.display();
      }
      if (ToolUnlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(145, 490, 200, 490);
        Tools t10 = new Tools(150, 500, 12);
        t10.display();
      }
      if (RoadUnlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(245, 490, 300, 490);
        Tools t11 = new Tools(250, 500, 13);
        t11.display();
      }
      if (HitBoxUnlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(345, 490, 415, 490);
        Tools t12 = new Tools(350, 500, 14);
        t12.display();
      }
    }
    else if (storemode == SKIN_UNLOCK)
    {
      //price here
      fill(0xff02F5DE);
      textSize(30);
      text("$20", 50, 300);
      text("$20", 150, 300);
      text("$20", 250, 300);
      text("$30", 350, 300);
      text("$50", 50, 400);
      text("$40", 150, 400);
      text("$60", 250, 400);
      text("$999", 350, 400);
      
      if (!onHow)
      {
        textSize(30);
        text("HOW TO CHANGE?", 50, 500);
      }
      else
      {
        textSize(25);
        text("CLICK 0-8 AFTER BUYING THE SKIN", 50, 500);
      }
      
      if (Skin1Unlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(45, 290, 100, 290);
        Circle c1 = new Circle(75, 325, 0, CIR_DIAMETER);
        c1.display(1);
      }
      if (Skin2Unlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(145, 290, 200, 290);
        Circle c2 = new Circle(175, 325, 0, CIR_DIAMETER);
        c2.display(2);
      }
      if (Skin3Unlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(245, 290, 300, 290);
        Circle c3 = new Circle(275, 325, 0, CIR_DIAMETER);
        c3.display(3);
      }
      if (Skin4Unlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(345, 290, 400, 290);
        c4.display(4);
      }
      if (Skin5Unlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(45, 390, 100, 390);
        c5.display(5);
      }
      if (Skin6Unlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(145, 390, 200, 390);
        c6.display(6);
      }
      if (Skin7Unlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(245, 390, 300, 390);
        c7.display(7);
      }
      if (Skin8Unlock)
      {
        stroke(0xff02F5DE);
        strokeWeight(5);
        line(345, 390, 410, 390);
        c8.display(8);
      }
    }
    
    textSize(18);
    fill(0xff0208F5);
    text("MONEY : " + money, 110, 210);
    
    text("~TOOLS UNLOCK~", 50, 250);
    text("~SKINS UNLOCK~", 250, 250);
    CheckTouch();

    
  } 
}
class TitleBar
{
  boolean onStart = false;
  boolean onShop = false;
  boolean onExit = false;
  ArrayList balls;
  int ballnum = 0;
  
  public TitleBar()
  {
    balls = new ArrayList();
    
    Circle c = new Circle(WIDTH/2, HEIGHT-20, 10, CIR_DIAMETER);
    c.isRunning = true;
    c.RandomDirection();
    balls.add(c);
    ballnum++;
  }
  
  public void ResetTitle()
  {
    for (int i = 0; i < ballnum; i++)
    {
      balls.remove(i);
      ballnum--;
    }
    
    if (highest == 0)
    {
      Circle c = new Circle(WIDTH/2, HEIGHT-20, 10,CIR_DIAMETER);
      balls.add(c);
      ballnum++;
    }
    else
    {
    for (int i = 0; i < highest; i++)
      {
        Circle c = new Circle(WIDTH/2, HEIGHT-20, 10, CIR_DIAMETER);
        balls.add(c);
        ballnum++;
      }
    }
    
    for (int i = 0; i < ballnum; i++)
    {
      Circle c = (Circle)balls.get(i);
      c.isRunning = true;
      c.RandomDirection();
    }
  }
  
  public void checkOnButton()
  {
    if (mouseX >= 150 && mouseX <= 360 && mouseY >= 280 && mouseY <= 370)
    {
      onStart = true;
    }
    else
    {
      onStart = false;
    }
    
    if (mouseX >= 150 && mouseX <= 360 && mouseY >= 400 && mouseY <= 490)
    {
      onShop = true;
    }
    else
    {
      onShop = false;
    }
    if (mouseX >= 150 && mouseX <= 360 && mouseY >= 520 && mouseY <= 610)
    {
      onExit = true;
    }
    else
    {
      onExit = false;
    }
  }
  
  public void display()
  {
    for (int i = 0; i < ballnum; i++)
       {
          Circle c = (Circle)balls.get(i);
          
          boolean Colli;
          Colli = c.CollisionWithBlock(100.f, 70.f, 300.f, 150.f);
          Colli = c.CollisionWithBlock(110.f, 80.f, 300.f, 150.f);
          
          Colli = c.CollisionWithBlock(160.f, 290.f, 200.f, 80.f);
          Colli = c.CollisionWithBlock(150.f, 280.f, 200.f, 80.f);
          
          Colli = c.CollisionWithBlock(160.f, 410.f, 200.f, 80.f);
          Colli = c.CollisionWithBlock(150.f, 400.f, 200.f, 80.f);
          
          Colli = c.CollisionWithBlock(160.f, 530.f, 200.f, 80.f);
          Colli = c.CollisionWithBlock(150.f, 520.f, 200.f, 80.f);
          
          c.movement();
          c.display(BallType);
       }
    
    noStroke();
    fill(0xff1BF0D5);
    rect(110, 80, 300, 150);
    fill(0xff2B1BF0);
    rect(100, 70, 300, 150);
    fill(0xffF01BE5);   
    
    textSize(50);
    PFont archristy = loadFont("ARCHRISTY-48.vlw");
    textFont(archristy);
    text("BALL", 110, 130);
    text("GAME", 270, 200);
    
    //START GAME BUTTON
    if (!onStart)
    {    
      fill(0xff0208F5);
      rect(160, 290, 200, 80);
      fill(0xff02F5DE);
      rect(150, 280, 200, 80);
      textSize(30);
      fill(0xff0208F5);
      text("START GAME", 170, 330);
    }
    else
    {
      fill(0xff02F5DE);
      rect(160, 290, 200, 80);
      fill(0xff0208F5);
      rect(150, 280, 200, 80);
      textSize(30);
      fill(0xff02F5DE);
      text("START GAME", 170, 330);
    }
    
    //STORE BUTTON
    if (!onShop)
    {
      fill(0xff0208F5);
      rect(160, 410, 200, 80);
      fill(0xff02F5DE);
      rect(150, 400, 200, 80);
      textSize(30);
      fill(0xff0208F5);
      text("STORE", 210, 450);
    }
    else
    {
      fill(0xff02F5DE);
      rect(160, 410, 200, 80);
      fill(0xff0208F5);
      rect(150, 400, 200, 80);
      textSize(30);
      fill(0xff02F5DE);
      text("STORE", 210, 450);
    }
    
    if (!onExit)
    {
      fill(0xff0208F5);
      rect(160, 530, 200, 80);
      fill(0xff02F5DE);
      rect(150, 520, 200, 80);
      textSize(30);
      fill(0xff0208F5);
      text("EXIT", 220, 570);
    }
    else
    {
      fill(0xff02F5DE);
      rect(160, 530, 200, 80);
      fill(0xff0208F5);
      rect(150, 520, 200, 80);
      textSize(30);
      fill(0xff02F5DE);
      text("EXIT", 220, 570);
    }
    
    textSize(18);
    fill(0xff0208F5);
    text("HIGHEST SCORE : " + highest, 110, 260);
    
    checkOnButton();
  }
}
final int ToolSize = 20;

class Tools
{
  PVector pos;
  boolean isTouch = false;
  boolean CancelAfterRound = false;
  int type;
  
  int mnum = 0;
  ArrayList minis = new ArrayList(mnum);
  int hnum = 0;
  ArrayList<Float> hxlist = new ArrayList<Float>(hnum);
  ArrayList<Float> hylist = new ArrayList<Float>(hnum);
  ArrayList<Integer> htlist = new ArrayList<Integer>(hnum);
  
  public Tools(float x, float y, int type)
  {
    pos = new PVector(x, y);
    this.type = type;
  }
  
  public void moveDown()
  {
    pos.y += BlockSize;
  }
  
  public boolean checkCollision()
  {
    for (int i = 0; i < cnum; i++)
    {
      Circle c = (Circle)circles.get(i);
      float dx = pos.x + BlockSize/2 - c.position.x;
      float dy = pos.y + BlockSize/2 - c.position.y;
      float minidis = ToolSize + c.diameter;
      if (sqrt(dx*dx+dy*dy) <= minidis)
      {
        isTouch = true;
      }
    }
    return isTouch;
  }
  
  public void ToolHorizontal()
  {
    if (checkCollision())
    {
      CancelAfterRound = true;
      strokeWeight(3);
      stroke(0xffFAFF17);
      line(0, pos.y + BlockSize/2, WIDTH, pos.y + BlockSize/2);
      for (int i = 0; i < bnum; i++)
      {
        BlockCreate bc = (BlockCreate)blocks.get(i);
        for (int j = 0; j < bc.blocknum; j++)
        {
          Block b = (Block)bc.lineBlocks.get(j);
          if (b.posy == pos.y)
          {
            b.getHit();
          }
        }
      }
      isTouch = false;
    }
  }
  
  public void ToolVertical()
  {
    if (checkCollision())
    {
      CancelAfterRound = true;
      strokeWeight(3);
      stroke(0xffFAFF17);
      line(pos.x + BlockSize/2, 0 , pos.x + BlockSize/2, HEIGHT);
      for (int i = 0; i < bnum; i++)
      {
        BlockCreate bc = (BlockCreate)blocks.get(i);
        for (int j = 0; j < bc.blocknum; j++)
        {
          Block b = (Block)bc.lineBlocks.get(j);
          if(b.posx == pos.x)
          {
            b.getHit();
          }
        }
      }
    }
    isTouch = false;
  }
  
  public void ToolMini()
  {
    if (checkCollision())
    {
      CancelAfterRound = true;
      for (int i = 0; i < 10; i++)
      {
        int ranx = (int)random(-50,50);
        int rany = (int)random(-50,50);
        MiniBall mb = new MiniBall(pos.x + BlockSize/2, pos.y + BlockSize/2, 12, ranx, rany);
        minis.add(mb);
        mnum++;
      }
    }
    isTouch = false;
  }
  
  public void checkMini()
  {
    for (int i = 0; i < mnum; i++)
    {
      boolean isQuit = false;
      MiniBall mb = (MiniBall)minis.get(i);
      mb.movement();
      mb.display();
      for (int m = 0; m < bnum; m++)
      {
        BlockCreate bc = (BlockCreate)blocks.get(m);
        for (int n = 0; n < bc.blocknum; n++)
        {
          Block b = (Block)bc.lineBlocks.get(n);
          if (mb.CollisionWithBlock(b.posx, b.posy))
          {
           b.getHit();
           isQuit = true;
          }
        }
      }
      if (mb.checkDisappear())
      {
        isQuit = true;
      }
      if (isQuit)
      {
        minis.remove(i);
        mnum--;
        i--;
      }
    }
  }
  
  public void ToolXray()
  {
    if (checkCollision())
    {
      CancelAfterRound = true;
      stroke(0xffAFFFEE);
      strokeWeight(10);
      float uprightx = pos.x + BlockSize/2;
      float uprighty = pos.y + BlockSize/2;
      while (uprightx <= WIDTH && uprighty >= 0)
      {
        uprightx += BlockSize;
        uprighty -= BlockSize;
      }
      float upleftx = pos.x + BlockSize/2;
      float uplefty = pos.y + BlockSize/2;
      while (upleftx >= 0 && uplefty >= 0)
      {
        upleftx -= BlockSize;
        uplefty -= BlockSize;
      }      
      float downrightx = pos.x + BlockSize/2;
      float downrighty = pos.y + BlockSize/2;
      while (downrightx <= WIDTH && downrighty <= HEIGHT)
      {
        downrightx += BlockSize;
        downrighty += BlockSize;
      }
      float downleftx = pos.x + BlockSize/2;
      float downlefty = pos.y + BlockSize/2;
      while (downleftx >= 0 && downrighty <= HEIGHT)
      {
        downleftx -= BlockSize;
        downlefty += BlockSize;
      }
      line(upleftx, uplefty, downrightx, downrighty);
      line(uprightx, uprighty, downleftx, downlefty);
      for (int i = 0; i < bnum; i++)
      {
        BlockCreate bc = (BlockCreate)blocks.get(i);
        for (int j = 0; j < bc.blocknum; j++)
        {
          Block b = (Block)bc.lineBlocks.get(j);
          if (b.posx - pos.x == b.posy - pos.y || b.posx - pos.x == -(b.posy - pos.y))
          {
            b.getHit();
          }
        }
      }
    }
    isTouch = false;
  }
  
  public void ToolBomb()
  {
    if (checkCollision())
    {
      CancelAfterRound = true;
      fill(0xffE1FF3B);
      stroke(0xffFF0000);
      strokeWeight(5);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, BlockSize*2, BlockSize*2);
      for (int i = 0; i < bnum; i++)
      {
        BlockCreate bc = (BlockCreate)blocks.get(i);
        for (int j = 0; j < bc.blocknum; j++)
        {
          Block b = (Block)bc.lineBlocks.get(j);
          if (b.posx == pos.x)
          {
            if (b.posy == pos.y - BlockSize || b.posy == pos.y + BlockSize)
            {
              b.getHit();
            }
          }
          if (b.posy == pos.y)
          {
            if (b.posx == pos.x - BlockSize || b.posx == pos.x + BlockSize)
            {
              b.getHit();
            }
          }
          if (b.posy == pos.y - BlockSize || b.posy == pos.y + BlockSize)
          {
            if (b.posx == pos.x - BlockSize || b.posx == pos.x + BlockSize)
            {
              b.getHit();
            }
          }
        }
      }
      isTouch = false;
    }
  }
  
  public void ToolDirection()
  {
    if (checkCollision())
    {
      CancelAfterRound = true;
      for (int i = 0; i < cnum; i++)
      {
        Circle c = (Circle)circles.get(i);
        float dx = pos.x + BlockSize/2 - c.position.x;
        float dy = pos.y + BlockSize/2 - c.position.y;
        float minidis = ToolSize + c.diameter;
        if (sqrt(dx*dx+dy*dy) <= minidis)
        {
           c.RandomDirection();
        }
      }
    }
  }
  
  public void ToolHit()
  {
    if (checkCollision())
    {
      CancelAfterRound = true;
      for (int i = 0; i < bnum; i++)
      {
        BlockCreate bc = (BlockCreate)blocks.get(i);
        for (int j = 0; j < bc.blocknum; j++)
        {
          Block b = (Block)bc.lineBlocks.get(j);
          int rantemp = (int)random(10);
          if (rantemp >= 8)
          {
            b.getHit();
            hxlist.add(b.posx + BlockSize/2);
            hylist.add(b.posy + BlockSize/2);
            htlist.add(30);
            hnum++;
          }
        }
      }
    }
  } 
  
  public void HitDisplay()
  {
    for (int i = 0; i < hnum; i++)
    {
      if (htlist.get(i) < 0)
      {
        hxlist.remove(i);
        hylist.remove(i);
        htlist.remove(i);
        hnum--;
      }
      else
      {
        fill(0xff00F70E);
        stroke(0xff1300F7);
        strokeWeight(4);
        
        pushMatrix();
        translate(hxlist.get(i), hylist.get(i));
        rotate(12);
        
        textSize(18);
        text("HIT!", 0, 0);
        popMatrix();
        
        htlist.set(i, htlist.get(i)-1); 
      }
    }
  }
  
  public void ToolShow()
  {
    if (checkCollision())
    {
      CancelAfterRound = true;
      for (int i = 0; i < bnum; i++)
      {
        BlockCreate bc = (BlockCreate)blocks.get(i);
        for (int j = 0; j < bc.blocknum; j++)
        {
          Block b = (Block)bc.lineBlocks.get(j);
          b.showHighBlock = true;
        }
      }
    }
    isTouch = false;
  }
  
  public void display()
  {
    if (type == 1)
    {
      fill(0);
      strokeWeight(2);
      stroke(0xffFFF931);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(0xffFFF931);
      line(pos.x + (BlockSize/2) - 5, pos.y + (BlockSize/2), pos.x + (BlockSize/2) + 5, pos.y + (BlockSize/2));
      line(pos.x + (BlockSize/2), pos.y + (BlockSize/2) - 5, pos.x + (BlockSize/2), pos.y + (BlockSize/2) + 5);
      
  }
    else if (type == 2)
    {
      fill(0);
      strokeWeight(2);
      stroke(0xff273EFF);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(0xff273EFF);
      line(pos.x + (BlockSize/2) - 5, pos.y + (BlockSize/2), pos.x + (BlockSize/2) + 5, pos.y + (BlockSize/2));
    }
    else if (type == 3)
    {
      fill(0);
      strokeWeight(2);
      stroke(0xff17B3FF);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(0xff17B3FF);
      line(pos.x + (BlockSize/2), pos.y + (BlockSize/2) - 5, pos.x + (BlockSize/2), pos.y + (BlockSize/2) + 5);
    }
    else if (type == 4)
    {
      fill(0);
      strokeWeight(2);
      stroke(0xffFC8B00); 
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(0xffFC8B00);
      ellipse(pos.x + (BlockSize/2), pos.y + (BlockSize/2) + 5, 2, 2);
      line(pos.x + (BlockSize/2) - 3, pos.y + (BlockSize/2) + 3, pos.x + (BlockSize/2) - 5, pos.y + (BlockSize/2));
      line(pos.x + (BlockSize/2) + 3, pos.y + (BlockSize/2) + 3, pos.x + (BlockSize/2) + 5, pos.y + (BlockSize/2));
      
      line(pos.x + (BlockSize/2), pos.y + (BlockSize/2) , pos.x + (BlockSize/2), pos.y + (BlockSize/2) - 4);
    }
    else if (type == 5)
    {
      fill(0);
      strokeWeight(2);
      stroke(0xffECF502);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      textSize(14);
      fill(0xffECF502);
      text('S', pos.x + BlockSize/2, pos.y + BlockSize/2 + 5);
      line(pos.x + (BlockSize/2), pos.y + (BlockSize/2) - 5, pos.x + (BlockSize/2), pos.y + (BlockSize/2) + 5);
    }
    else if (type == 6)
    {
      fill(0xffECF502);
      strokeWeight(2);
      stroke(0xff12FF2B);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      textSize(14);
      fill(0);
      text('S', pos.x + BlockSize/2, pos.y + BlockSize/2 + 5);
      line(pos.x + (BlockSize/2), pos.y + (BlockSize/2) - 5, pos.x + (BlockSize/2), pos.y + (BlockSize/2) + 5);
    }
    else if (type == 7)
    {
      fill(0);
      strokeWeight(2);
      stroke(0xffFF1212);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(0xffFF1212);
      ellipse(pos.x + BlockSize/2 - 1, pos.y + BlockSize/2 + 2, 8, 8);
      line(pos.x + BlockSize/2 - 1, pos.y + BlockSize/2 + 2, pos.x + BlockSize/2 + 4, pos.y + BlockSize/2 - 4);
    }
    else if (type == 8)
    {
      fill(0);
      strokeWeight(2);
      stroke(0xffFF03F3);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      line(pos.x + (BlockSize/2) - 5, pos.y + (BlockSize/2) - 5, pos.x + (BlockSize/2) + 5, pos.y + (BlockSize/2) + 5);
      line(pos.x + (BlockSize/2) - 5, pos.y + (BlockSize/2) + 5, pos.x + (BlockSize/2) + 5, pos.y + (BlockSize/2) - 5);  
    }
    else if (type == 9)
    {
      fill(0);
      strokeWeight(2);
      stroke(0xffBCFF15);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(0xffBCFF15);
      ellipse(pos.x + BlockSize/2 - ToolSize/3, pos.y + BlockSize/2 - ToolSize/3, ToolSize/3, ToolSize/3);
      ellipse(pos.x + BlockSize/2 - ToolSize/3, pos.y + BlockSize/2 + ToolSize/3, ToolSize/3, ToolSize/3);
      ellipse(pos.x + BlockSize/2 + ToolSize/3, pos.y + BlockSize/2 - ToolSize/3, ToolSize/3, ToolSize/3);
      ellipse(pos.x + BlockSize/2 + ToolSize/3, pos.y + BlockSize/2 + ToolSize/3, ToolSize/3, ToolSize/3);
    }
    else if (type == 10)
    {
      fill(0);
      strokeWeight(2);
      stroke(0xff15FFF9);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(0xff15FFF9);
      ellipse(pos.x + BlockSize/2 - ToolSize/4, pos.y + BlockSize/2, ToolSize/4, ToolSize/4);
      ellipse(pos.x + BlockSize/2 + ToolSize/4, pos.y + BlockSize/2, ToolSize/4, ToolSize/4);
      strokeWeight(4);
      line(pos.x + BlockSize/2 - ToolSize/2 + 3, pos.y + BlockSize/2, pos.x + BlockSize/2 + ToolSize/2 - 3, pos.y + BlockSize/2);
    }
    else if (type == 11)
    {
      fill(0);
      strokeWeight(2);
      stroke(255);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
    }
    else if (type == 12)
    {
      fill(0);
      int ranr = (int)random(225);
      int ranb = (int)random(225);
      int rang = (int)random(225);
      strokeWeight(5);
      stroke(ranr, ranb, rang);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
    }
    else if (type == 13)
    {
      fill(0);
      strokeWeight(2);
      stroke(0xff36FF41);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(0xff36FF41);
      strokeWeight(5);
      line(pos.x + BlockSize/2, pos.y + BlockSize/2 + ToolSize/2, pos.x + BlockSize/2 - ToolSize/2, pos.y + BlockSize/2);
      line(pos.x + BlockSize/2 - ToolSize/2, pos.y + BlockSize/2, pos.x + BlockSize/2, pos.y + BlockSize/2 - ToolSize/2);
    }
    else if (type == 14)
    {
      fill(255);
      strokeWeight(2);
      stroke(0xffF70004);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(0xffF70004);
      textSize(18);
      text("?", pos.x + BlockSize/2 - 5, pos.y + BlockSize/2 + ToolSize/2);
    }
  }
  
 
}
  public void settings() {  size(500, 700); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BallGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
