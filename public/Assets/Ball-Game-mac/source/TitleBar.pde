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
  
  void ResetTitle()
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
  
  void checkOnButton()
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
  
  void display()
  {
    for (int i = 0; i < ballnum; i++)
       {
          Circle c = (Circle)balls.get(i);
          
          boolean Colli;
          Colli = c.CollisionWithBlock(100., 70., 300., 150.);
          Colli = c.CollisionWithBlock(110., 80., 300., 150.);
          
          Colli = c.CollisionWithBlock(160., 290., 200., 80.);
          Colli = c.CollisionWithBlock(150., 280., 200., 80.);
          
          Colli = c.CollisionWithBlock(160., 410., 200., 80.);
          Colli = c.CollisionWithBlock(150., 400., 200., 80.);
          
          Colli = c.CollisionWithBlock(160., 530., 200., 80.);
          Colli = c.CollisionWithBlock(150., 520., 200., 80.);
          
          c.movement();
          c.display(BallType);
       }
    
    noStroke();
    fill(#1BF0D5);
    rect(110, 80, 300, 150);
    fill(#2B1BF0);
    rect(100, 70, 300, 150);
    fill(#F01BE5);   
    
    textSize(50);
    PFont archristy = loadFont("ARCHRISTY-48.vlw");
    textFont(archristy);
    text("BALL", 110, 130);
    text("GAME", 270, 200);
    
    //START GAME BUTTON
    if (!onStart)
    {    
      fill(#0208F5);
      rect(160, 290, 200, 80);
      fill(#02F5DE);
      rect(150, 280, 200, 80);
      textSize(30);
      fill(#0208F5);
      text("START GAME", 170, 330);
    }
    else
    {
      fill(#02F5DE);
      rect(160, 290, 200, 80);
      fill(#0208F5);
      rect(150, 280, 200, 80);
      textSize(30);
      fill(#02F5DE);
      text("START GAME", 170, 330);
    }
    
    //STORE BUTTON
    if (!onShop)
    {
      fill(#0208F5);
      rect(160, 410, 200, 80);
      fill(#02F5DE);
      rect(150, 400, 200, 80);
      textSize(30);
      fill(#0208F5);
      text("STORE", 210, 450);
    }
    else
    {
      fill(#02F5DE);
      rect(160, 410, 200, 80);
      fill(#0208F5);
      rect(150, 400, 200, 80);
      textSize(30);
      fill(#02F5DE);
      text("STORE", 210, 450);
    }
    
    if (!onExit)
    {
      fill(#0208F5);
      rect(160, 530, 200, 80);
      fill(#02F5DE);
      rect(150, 520, 200, 80);
      textSize(30);
      fill(#0208F5);
      text("EXIT", 220, 570);
    }
    else
    {
      fill(#02F5DE);
      rect(160, 530, 200, 80);
      fill(#0208F5);
      rect(150, 520, 200, 80);
      textSize(30);
      fill(#02F5DE);
      text("EXIT", 220, 570);
    }
    
    textSize(18);
    fill(#0208F5);
    text("HIGHEST SCORE : " + highest, 110, 260);
    
    checkOnButton();
  }
}