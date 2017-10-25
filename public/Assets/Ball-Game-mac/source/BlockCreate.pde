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