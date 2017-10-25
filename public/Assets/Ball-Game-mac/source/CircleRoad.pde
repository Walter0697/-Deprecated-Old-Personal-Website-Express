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