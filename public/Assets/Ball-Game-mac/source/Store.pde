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
  
  void CheckTouch()
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
        fill(#02F5DE);
        text("~TOOLS UNLOCK~", 50, 250);
    }
    if (mouseX >= 250 && mouseX <= 400 && mouseY >= 230 && mouseY <= 250)
    {
        fill(#02F5DE);
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
  
  void display()
  {
    noStroke();
    fill(#1BF0D5);
    rect(110, 80, 300, 100);
    fill(#2B1BF0);
    rect(100, 70, 300, 100);
    fill(#F01BE5);  
    textSize(50);
    text("STORE", 180, 140);

    if (!onExit)
    {
      fill(#0208F5);
      rect(160, 560, 200, 80);
      fill(#02F5DE);
      rect(150, 550, 200, 80);
      textSize(30);
      fill(#0208F5);
      text("EXIT", 220, 600);
    }
    else
    {
      fill(#02F5DE);
      rect(160, 560, 200, 80);
      fill(#0208F5);
      rect(150, 550, 200, 80);
      textSize(30);
      fill(#02F5DE);
      text("EXIT", 220, 600);
    }
    
    if (storemode == TOOL_UNLOCK)
    {
      //price here
      fill(#02F5DE);
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
        stroke(#02F5DE);
        strokeWeight(5);
        line(45, 290, 100, 290);
        Tools t1 = new Tools(50, 300, 2);
        t1.display();
      }
      if (VertiUnlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(145, 290, 200, 290);
        Tools t2 = new Tools(150, 300, 3);
        t2.display();
      }
      if (RandUnlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(245, 290, 300, 290);
        Tools t3 = new Tools(250, 300, 4);
        t3.display();
      }
      if (SpecUnlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(345, 290, 400, 290);
        Tools t4 = new Tools(350, 300, 6);
        t4.display();
      }
      if (BombUnlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(45, 390, 100, 390);
        Tools t5 = new Tools(50, 400, 7);
        t5.display();
      }
      if (XrayUnlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(145, 390, 200, 390);
        Tools t6 = new Tools(150, 400, 8);
        t6.display();
      }
      if (MiniUnlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(245, 390, 300, 390);
        Tools t7 = new Tools(250, 400, 9);
        t7.display();
      }
      if (CleanUnlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(345, 390, 400, 390);
        Tools t8 = new Tools(350, 400, 10);
        t8.display();
      }
      if (ShowUnlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(45, 490, 100, 490);
        Tools t9 = new Tools(50, 500, 11);
        t9.display();
      }
      if (ToolUnlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(145, 490, 200, 490);
        Tools t10 = new Tools(150, 500, 12);
        t10.display();
      }
      if (RoadUnlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(245, 490, 300, 490);
        Tools t11 = new Tools(250, 500, 13);
        t11.display();
      }
      if (HitBoxUnlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(345, 490, 415, 490);
        Tools t12 = new Tools(350, 500, 14);
        t12.display();
      }
    }
    else if (storemode == SKIN_UNLOCK)
    {
      //price here
      fill(#02F5DE);
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
        stroke(#02F5DE);
        strokeWeight(5);
        line(45, 290, 100, 290);
        Circle c1 = new Circle(75, 325, 0, CIR_DIAMETER);
        c1.display(1);
      }
      if (Skin2Unlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(145, 290, 200, 290);
        Circle c2 = new Circle(175, 325, 0, CIR_DIAMETER);
        c2.display(2);
      }
      if (Skin3Unlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(245, 290, 300, 290);
        Circle c3 = new Circle(275, 325, 0, CIR_DIAMETER);
        c3.display(3);
      }
      if (Skin4Unlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(345, 290, 400, 290);
        c4.display(4);
      }
      if (Skin5Unlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(45, 390, 100, 390);
        c5.display(5);
      }
      if (Skin6Unlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(145, 390, 200, 390);
        c6.display(6);
      }
      if (Skin7Unlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(245, 390, 300, 390);
        c7.display(7);
      }
      if (Skin8Unlock)
      {
        stroke(#02F5DE);
        strokeWeight(5);
        line(345, 390, 410, 390);
        c8.display(8);
      }
    }
    
    textSize(18);
    fill(#0208F5);
    text("MONEY : " + money, 110, 210);
    
    text("~TOOLS UNLOCK~", 50, 250);
    text("~SKINS UNLOCK~", 250, 250);
    CheckTouch();

    
  } 
}