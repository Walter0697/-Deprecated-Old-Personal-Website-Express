  import java.io.BufferedReader;
  
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
  
  void setup()
  {
    size(500, 700);
    tb = new TitleBar();
    st = new Store();
    
    blocks = new ArrayList(bnum);
    circles = new ArrayList(cnum);
    loadData();
    tb.ResetTitle();
  }
  
  void draw()
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
        fill(#2EFFEC);
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
  
  void mousePressed()
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
  
  void keyPressed()
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
    
  void startGame()
  {
    ci = new Circle(random(0, WIDTH), EDGE, 10, CIR_DIAMETER);
    b = new BlockCreate(NUMBER, false);
    blocks.add(b);
    circles.add(ci);
    bnum++;
    cnum++;
  }
  
  void resetAllData()
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
    fill(#D80D0D);
    rect(WIDTH/2 - 140, HEIGHT/2 - 40, 300, 100);
    fill(#0DD8AE);
    rect(WIDTH/2 - 150, HEIGHT/2 - 50, 300, 100);
    fill(#0300FA);
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
        money = int(pieces[0]);
        highest = int(pieces[1]);
        HoriUnlock = boolean(pieces[2]);
        VertiUnlock = boolean(pieces[3]);
        RandUnlock = boolean(pieces[4]);
        SpecUnlock = boolean(pieces[5]);
        BombUnlock = boolean(pieces[6]);
        XrayUnlock = boolean(pieces[7]);
        MiniUnlock = boolean(pieces[8]);
        CleanUnlock = boolean(pieces[9]);
        ShowUnlock = boolean(pieces[10]);
        ToolUnlock = boolean(pieces[11]);
        RoadUnlock = boolean(pieces[12]);
        HitBoxUnlock = boolean(pieces[13]);
        Skin1Unlock = boolean(pieces[14]);
        Skin2Unlock = boolean(pieces[15]);
        Skin3Unlock = boolean(pieces[16]);
        Skin4Unlock = boolean(pieces[17]);
        Skin5Unlock = boolean(pieces[18]);
        Skin6Unlock = boolean(pieces[19]);
        Skin7Unlock = boolean(pieces[20]);
        Skin8Unlock = boolean(pieces[21]);
      }
    }catch(Exception e){}     
  }
  