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
      stroke(#FC0000);
      fill(0);
      rect(posx, posy, BlockSize, BlockSize);
      fill(#FC0000);
    }
    else if (number <= 10)
    {
      stroke(#FF9A03);
      fill(0);
      rect(posx, posy, BlockSize, BlockSize);
      fill(#FF9A03);
    }
    else if (number <= 20)
    {
      stroke(#23FF03);
      fill(0);
      rect(posx, posy, BlockSize, BlockSize);
      fill(#23FF03);
    }
    else if (number <= 30)
    {
      stroke(#03FF76);
      fill(0);
      rect(posx, posy, BlockSize, BlockSize);
      fill(#03FF76);
    }
    else if (number <= 40)
    {
      stroke(#03FFF0);
      fill(0);
      rect(posx, posy, BlockSize, BlockSize);
      fill(#03FFF0);
    }
    else if (number <= 50)
    {
      stroke(#0354FF);
      fill(0);
      rect(posx, posy, BlockSize, BlockSize);
      fill(#0354FF);
    }
    else if (number <= 60)
    {
      stroke(#FA03FF);
      fill(0);
      rect(posx, posy, BlockSize, BlockSize);
      fill(#FA03FF);
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