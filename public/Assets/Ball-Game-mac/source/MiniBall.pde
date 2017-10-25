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
    fill(#EEFF0D);
    noStroke();
    ellipse(pos.x, pos.y, MINI_SIZE, MINI_SIZE);
  }
}