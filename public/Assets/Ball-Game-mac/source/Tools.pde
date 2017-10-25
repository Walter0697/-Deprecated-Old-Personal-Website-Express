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
      stroke(#FAFF17);
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
      stroke(#FAFF17);
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
      stroke(#AFFFEE);
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
      fill(#E1FF3B);
      stroke(#FF0000);
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
        fill(#00F70E);
        stroke(#1300F7);
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
      stroke(#FFF931);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(#FFF931);
      line(pos.x + (BlockSize/2) - 5, pos.y + (BlockSize/2), pos.x + (BlockSize/2) + 5, pos.y + (BlockSize/2));
      line(pos.x + (BlockSize/2), pos.y + (BlockSize/2) - 5, pos.x + (BlockSize/2), pos.y + (BlockSize/2) + 5);
      
  }
    else if (type == 2)
    {
      fill(0);
      strokeWeight(2);
      stroke(#273EFF);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(#273EFF);
      line(pos.x + (BlockSize/2) - 5, pos.y + (BlockSize/2), pos.x + (BlockSize/2) + 5, pos.y + (BlockSize/2));
    }
    else if (type == 3)
    {
      fill(0);
      strokeWeight(2);
      stroke(#17B3FF);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(#17B3FF);
      line(pos.x + (BlockSize/2), pos.y + (BlockSize/2) - 5, pos.x + (BlockSize/2), pos.y + (BlockSize/2) + 5);
    }
    else if (type == 4)
    {
      fill(0);
      strokeWeight(2);
      stroke(#FC8B00); 
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(#FC8B00);
      ellipse(pos.x + (BlockSize/2), pos.y + (BlockSize/2) + 5, 2, 2);
      line(pos.x + (BlockSize/2) - 3, pos.y + (BlockSize/2) + 3, pos.x + (BlockSize/2) - 5, pos.y + (BlockSize/2));
      line(pos.x + (BlockSize/2) + 3, pos.y + (BlockSize/2) + 3, pos.x + (BlockSize/2) + 5, pos.y + (BlockSize/2));
      
      line(pos.x + (BlockSize/2), pos.y + (BlockSize/2) , pos.x + (BlockSize/2), pos.y + (BlockSize/2) - 4);
    }
    else if (type == 5)
    {
      fill(0);
      strokeWeight(2);
      stroke(#ECF502);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      textSize(14);
      fill(#ECF502);
      text('S', pos.x + BlockSize/2, pos.y + BlockSize/2 + 5);
      line(pos.x + (BlockSize/2), pos.y + (BlockSize/2) - 5, pos.x + (BlockSize/2), pos.y + (BlockSize/2) + 5);
    }
    else if (type == 6)
    {
      fill(#ECF502);
      strokeWeight(2);
      stroke(#12FF2B);
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
      stroke(#FF1212);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(#FF1212);
      ellipse(pos.x + BlockSize/2 - 1, pos.y + BlockSize/2 + 2, 8, 8);
      line(pos.x + BlockSize/2 - 1, pos.y + BlockSize/2 + 2, pos.x + BlockSize/2 + 4, pos.y + BlockSize/2 - 4);
    }
    else if (type == 8)
    {
      fill(0);
      strokeWeight(2);
      stroke(#FF03F3);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      line(pos.x + (BlockSize/2) - 5, pos.y + (BlockSize/2) - 5, pos.x + (BlockSize/2) + 5, pos.y + (BlockSize/2) + 5);
      line(pos.x + (BlockSize/2) - 5, pos.y + (BlockSize/2) + 5, pos.x + (BlockSize/2) + 5, pos.y + (BlockSize/2) - 5);  
    }
    else if (type == 9)
    {
      fill(0);
      strokeWeight(2);
      stroke(#BCFF15);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(#BCFF15);
      ellipse(pos.x + BlockSize/2 - ToolSize/3, pos.y + BlockSize/2 - ToolSize/3, ToolSize/3, ToolSize/3);
      ellipse(pos.x + BlockSize/2 - ToolSize/3, pos.y + BlockSize/2 + ToolSize/3, ToolSize/3, ToolSize/3);
      ellipse(pos.x + BlockSize/2 + ToolSize/3, pos.y + BlockSize/2 - ToolSize/3, ToolSize/3, ToolSize/3);
      ellipse(pos.x + BlockSize/2 + ToolSize/3, pos.y + BlockSize/2 + ToolSize/3, ToolSize/3, ToolSize/3);
    }
    else if (type == 10)
    {
      fill(0);
      strokeWeight(2);
      stroke(#15FFF9);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(#15FFF9);
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
      stroke(#36FF41);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(#36FF41);
      strokeWeight(5);
      line(pos.x + BlockSize/2, pos.y + BlockSize/2 + ToolSize/2, pos.x + BlockSize/2 - ToolSize/2, pos.y + BlockSize/2);
      line(pos.x + BlockSize/2 - ToolSize/2, pos.y + BlockSize/2, pos.x + BlockSize/2, pos.y + BlockSize/2 - ToolSize/2);
    }
    else if (type == 14)
    {
      fill(255);
      strokeWeight(2);
      stroke(#F70004);
      ellipse(pos.x + BlockSize/2, pos.y + BlockSize/2, ToolSize, ToolSize);
      fill(#F70004);
      textSize(18);
      text("?", pos.x + BlockSize/2 - 5, pos.y + BlockSize/2 + ToolSize/2);
    }
  }
  
 
}