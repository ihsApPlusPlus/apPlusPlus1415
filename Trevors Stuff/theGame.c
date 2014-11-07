#include <gb/gb.h>
#include <stdio.h>
#include <gb/drawing.h>
#include <gb/console.h>



UBYTE x=150;
UBYTE y=20;
int key;
int i = 0;
int touch = 0;
int move = 0;
int uplimit;
int xbound;
int ybound;
int boundarry[160]; 
int loselocsx[160];
int loselocsy[136];
int tileconst = 8;
int background = 0;
int power1 = 0;
int power2 = 0;
int deaths = 0;

void main();

unsigned char smile[] =
{
  0x3C,0x3C,0x42,0x42,0x81,0xA5,0x81,0x91, 
  0x81,0x85,0x81,0xB9,0x42,0x42,0x3C,0x3C,               
};

unsigned char bkgdata[] =//graphic for background, spikes, blank tile, and win graphic thing
{
  0x00,0x81,0x00,0x42,0x00,0x24,0x99,0x18,
  0x99,0x18,0x00,0x24,0x00,0x42,0x00,0x81,
  0x00,0x00,0x00,0x81,0x00,0xE7,0xE7,0x18,
  0xE7,0x18,0x00,0xE7,0x00,0x81,0x00,0x00,
  0x00,0x81,0x24,0x81,0x99,0x81,0x00,0x81, 
  0x81,0x24,0x81,0x42,0x81,0x81,0xE7,0x99, 
  0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
  0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
  0x24,0x18,0x24,0x42,0xB9,0x42,0x18,0x42,
  0x24,0x18,0x24,0x42,0xB9,0x42,0x18,0x42,
  0x0A,0x14,0x1E,0x00,0x00,0x1E,0x14,0x0A,
  0x0A,0x14,0x1E,0x00,0x00,0x1E,0x14,0x0A
};

//these char arrayss define what tile is what

/* This array states that tile number 1 will be displayed */
unsigned char bkgtilea[1] = { 1 };

/* This array states that tile number 2 will be displayed */
unsigned char bkgtileb[1] = { 2 };

unsigned char enemy[1] = { 3 };
unsigned char blank[1] = { 4 };
unsigned char powerup[1] = { 5 };
unsigned char winthingy[1] = { 6 };
 

void makeBackground(int backnum)//method builds backgrounds
{
//backnum determines what screen to build
int k = 0;
int i;
int q;
if(backnum==0){
  /*
    This line states that data for the background tiles is defined in
    'bkgdata'. From tile 1 for 3 tiles in length
   */
  set_bkg_data(1,6,bkgdata);  

  
  if(deaths==1){
	set_bkg_tiles(7,14,1,1,powerup);
  }
  /*
  
    This loop states that tile number 1 (from bkgdata) should be
    displayed as defined by the loop
   */
for(i=0 ; i<20 ; i++)
 {
 set_bkg_tiles(i,0,1,1,bkgtilea);    /*  x,y,w,h,tilenumber */
 }

/* and so on */
for(i=1 ; i<17 ; i++)
 {
 set_bkg_tiles(0,i,1,1,bkgtilea);
 }

for(i=1 ; i<17 ; i++)
 {
 set_bkg_tiles(19,i,1,1,bkgtilea);
 }

for(i=0 ; i<20 ; i++)
 {
 set_bkg_tiles(i,17,1,1,bkgtilea);
 }

for(i=0 ; i<20 ; i++){
	if(i==13 || i==12){
	boundarry[i] = 300;
	}
	else{
 set_bkg_tiles(i,15,1,1,bkgtileb);

	boundarry[i] = 130;
	}
 }
 }
 if(backnum==1){
	k = 0;
	for(i = 1; i < 19; i++){
		for(q=1; q < 16; q++){
			set_bkg_tiles(i,q,1,1,blank);
		}
	}
	
	for(i = 0; i < 10; i++){
		set_bkg_tiles(i,14,1,1,bkgtileb);
			boundarry[i] = 122;
			k++;

	}
	for(i = 10; i < 20; i++){
		set_bkg_tiles(i,15,1,1,bkgtileb);

			boundarry[i] = 130;
			k++;

	}
	
 }
 if(backnum==2){
	for(i = 1; i < 19; i++){
		for(q=1; q < 16; q++){
			set_bkg_tiles(i,q,1,1,blank);
		}
	}
	
	for(i = 0; i < 10; i++){
		set_bkg_tiles(i,15,1,1,bkgtileb);
			boundarry[i] = 130;
			k++;

	}
	for(i = 0; i < 7; i++){
		set_bkg_tiles(i,14,1,1,bkgtileb);
			boundarry[i] = 122;
			k++;

	}
	for(i = 10; i < 20; i++){
		set_bkg_tiles(i,14,1,1,bkgtileb);

			boundarry[i] = 122;
			k++;

	}
	
	set_bkg_tiles(9,14,1,1,enemy);
	
	for(i = 0; i < 8; i++){
		for(k = 0; k < 8; k++){
			loselocsy[14*8 + k] = 1;
		}
		loselocsx[9*8 + i] = 1;
	}
	set_bkg_tiles(8,14,1,1,enemy);
	
	for(i = 0; i < 8; i++){
		for(k = 0; k < 8; k++){
			loselocsy[14*8 + k] = 1;
		}
		loselocsx[8*8 + i] = 1;
	}
	set_bkg_tiles(7,14,1,1,enemy);
	for(i = 0; i < 8; i++){
		for(k = 0; k < 8; k++){
			loselocsy[14*8 + k] = 1;
		}
		loselocsx[7*8 + i] = 1;
	}
	
 }
 if(backnum==3){
	set_bkg_tiles(17,4,1,1,powerup);
 }
 if(backnum==4){
	for(i = 1; i < 19; i++){
		for(q=1; q < 16; q++){
			set_bkg_tiles(i,q,1,1,blank);
		}
	}
	for(i = 0; i < 20; i++){
		set_bkg_tiles(i,15,1,1,bkgtileb);
			boundarry[i] = 130;
	}
	set_bkg_tiles(8,14,1,1,winthingy);
	for(i = 0; i < 8; i++){
		for(k = 0; k < 8; k++){
			loselocsy[14*8 + k] = 0;
		}
		loselocsx[7*8 + i] = 0;
	}
	for(i = 0; i < 8; i++){
		for(k = 0; k < 8; k++){
			loselocsy[15*8 + k] = 1;
		}
		loselocsx[8*8 + i] = 1;
	}
 }
 
 if(backnum==10){
	k=0;
	for(i = 0; i < 20; i++){
		for(q=0; q < 17; q++){
			set_bkg_tiles(i,q,1,1,blank);
		}
		for(q=0; q<8; q++){
			boundarry[k] = 0;
			k++;
		}
	}
 }


SHOW_BKG;     /* I wonder what that does ?! */


}   

void gameover(){//gameover
	deaths++;
	printf("GameOver");
	waitpad(J_SELECT);
	main();
}

void clear()//clears screen
{           
	
	int i;
	int q;
	set_bkg_data(1,6,bkgdata); 
	for(i = 0; i < 20; i++){
		for(q=0; q < 17; q++){
			set_bkg_tiles(i,q,1,1,blank);
		}

	}             
}

void main()
{

//initialization of stuff
	printf("Welcome to the Game! \nPress Enter to Begin");
	waitpad(J_START);//greeting that waits for start to be pressed until it continues
start:
	x=150;
    y=20;
    key;
    i = 0;
    touch = 0;
    move = 0;
    tileconst = 8;
    background = 0;
    power1 = 0;
	power2 = 0;
	for(i = 0; i < 160; i++){
		loselocsx[i] = 0;
	}
	for(i = 0; i < 136; i++){
		loselocsy[i] = 0;
	}
	i = 0;
	
	clear();
		makeBackground(0);

		
		while(1 > 0){
		
		
		if(i == 275){//checks where to put the ground
		int r = boundarry[((x + 7) / 8) - 1];
		int t = boundarry[(x / 8) - 1];
		if(r <= t){
			ybound = r;
		}
		else{
			ybound = t;
		}

		xbound = 0;
		//uplimit = 50;
	  /* sets the complete sprite data from tile 0 for 1 tile of smile
		 (data begins at tile 0 and ends after 1 tile) */
			set_sprite_data(0,1,smile);
	  /* sets tile number 0 to tile 0 of smile */
			set_sprite_tile(0,0);//sets hero to smile
			set_sprite_tile(1,0);//sets enemy dude to smile
			key=joypad();//sets up keypad 
			if(background==0 && y==ybound && x>=(6*8) && x<=(7*8) && deaths==1){//check for whther touching first powerup
				power2=1;
				set_bkg_tiles(7,14,1,1,blank);
			}
			if(background==3){//moves enemy when you get to the 4th screen
				move_sprite(1,50,y);
			}
			else{//hides enemy if not on the right screen
				move_sprite(1,0,0);
			}
			if(power1==1){//hides enemy when you get the powerup
				move_sprite(1,0,0);
			}
			if(background==3 && x==58 && power1==0){//game over check for enemy
				gameover();
			}
			if(background==3 && (x>136 && x<144) && power1==0 && (key & J_UP)){//how you get the secret powerup 
				power1 = 1;
				set_bkg_tiles(17,4,1,1,blank);
			}
			
			
				if(y==170 || (loselocsy[y - 8] == 1 && loselocsx[x] == 1 && background!=4)){//gameover check for bounds and spikes
					gameover();
				}
				if(loselocsy[y - 8] == 1 && loselocsx[x] == 1 && background==4){//win scenario
					printf("You Win!");
				}
				//game physics
				if(move==0 && y!=ybound){
					y++;
				}
				if(move==1){
					y--;
				}
				if(y==uplimit){
					move = 0;
				}
				
				if(key & J_SELECT){//restart buton
					power2 = 0;
					goto start;
				}
				if((key & J_UP) && y==ybound && move==0){//when you can jump
					move = 1;
					touch = 0;
					uplimit = y - 20;
					if(power2==1){
						uplimit -= x/3;
					}
					y--;
				}
				if(y<=boundarry[((x + 7) / 8) -1]){//how to check for walls (this is the weird condition i couldnt get to work
				if(key & J_RIGHT){
					if(x!=160){
						x++;
					}
				}
				}
				
				if(y<=boundarry[((x - 1) / 8) - 1]){//more walls
				if(key & J_LEFT){
					if(x!=5){
						x--;
					}
				}
				}
				
				
				if(x<=8){//changing screens
					x=150;
					y-=10;
					background++;
					makeBackground(background);
					move = 0;
					//printf("%d", boundarry[10]);
				}
				
				  /* moves sprite 0 to X-75 Y-75 */
			move_sprite(0,x,y);
			i = 0;
		}
		i++;
		SHOW_SPRITES;//shows sprites
		}
}

