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
    float halfAngle = angle/2.0;
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
      fill(#FC1919);
      stroke(#ECFF21);
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
      fill(#FF21E6);
      ellipse(position.x, position.y, diameter, diameter);
      fill(#3E21FF);
      ellipse(position.x, position.y, diameter-2, diameter-2);
      fill(#21FFE0);
      ellipse(position.x, position.y, diameter-4, diameter-4);
      fill(#2BFF21);
      ellipse(position.x, position.y, diameter-6, diameter-6);
      fill(#ECFF21);
      ellipse(position.x, position.y, diameter-8, diameter-8);
      fill(#FF2121);
      ellipse(position.x, position.y, diameter-10, diameter-10);
    }
    else if (bt == 4)
    {
      pushMatrix();
      translate(position.x, position.y);
      rotate(showTime/10.);
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
      rotate(showTime/10.);
      fill(255);
      stroke(#F7F5DC);
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
      rotate(showTime/30.);
      noStroke();
      fill(#FFC455);
      ellipse(0, 0, diameter, diameter);
      fill(#FF0505);
      star(0, 0, ToolSize/4, 3, 5);
      popMatrix();
    }
    else if (bt == 7)
    {
      noStroke();
      pushMatrix();
      translate(position.x, position.y);
      rotate(showTime/30.);
      fill(220, 46, 229, 80);
      ellipse(0, 0, diameter + 5, diameter + 5);
      fill(#DC2EE5);
      ellipse(0, 0, diameter, diameter);
      stroke(#55F2FF);
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
        
        fill(#55F2FF);
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