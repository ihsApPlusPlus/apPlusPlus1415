;1-P Pong by Trevor Morton
	processor 6502
	include vcs.h
	org $F000

YPosForPlay = $82;
VisiblePlayerLine = $83;
PlayerBuffer = $84
BallDirection = $90
VisibleBallLine = $86
YPosForBall = $87
BallBuffer = $88
BallSpeed = $89
BallFrame = $85
PlayFieldBuffer = $91
PlayerHeight = $92
LevelingBol = $93
TimeComp0 = $94
TimeComp1 = $95
GameOverBool = $96
WallCollisionBool = $97
WallDirectionBool = $98
PlayerDirectionBool = $99

;generic start up stuff...
Start
	SEI	
	CLD  	
	LDX #$FF	;we're so clever, we LDX
	TXS		;once and use that for 
	LDA #0		;both TXS ad the ClearMem loop
ClearMem 
	STA 0,X		
	DEX		
	BNE ClearMem	
	LDA #$CC	
	STA COLUBK;backround color	
	
	STA RESM1;missle wall position
	
	LDA #$03	;color value
	STA COLUP0
	LDA #$00;color value
	STA COLUP1

	LDA #0
	STA GameOverBool;booleans to keep track of things
	STA WallCollisionBool
	STA WallDirectionBool

	LDA #2
	STA ENAM1;enables missle 1
	
	LDA #25;part of the timer
	STA TimeComp0
	STA TimeComp1
	
	LDA #16;player height and leveling
	STA PlayerHeight
	LDA #1
	STA LevelingBol
	
	LDA #90;initial player position
	STA YPosForPlay
	
	LDA #%00000000
	STA SWACNT
	
	LDA #0;ball initial setup
	STA ENABL
	LDA #16
	STA COLUPF
	LDA #150
	STA YPosForBall
	LDA #$F0
	STA HMBL
	LDA #4
	STA BallSpeed
	STA BallFrame
	LDA #0
	STA BallDirection
	
	LDA #%00110000
	STA CTRLPF
	
	LDA #0
	STA PlayFieldBuffer

	LDA #%00000000  ;put value of 1 in the left nibble (slow move left)
	STA HMM1	;set the move for missile 0
	
	;NUSIZ0 sets the size and multiplying
	;of the sprite and missiles --see the Stella 
	;guide for details
	LDA #%00011000	
	STA NUSIZ0 ;Quad Width for now


;VSYNC time
MainLoop
	
	LDA  #2;initial syncs
	STA  VSYNC	
	STA  WSYNC	
	STA  WSYNC 	
	STA  WSYNC	
	LDA  #43	
	STA  TIM64T	
	LDA #0		
	STA  VSYNC 	
	

LevelingTimer;timer for leveling
	LDA TimeComp0
	BEQ Timer2
	DEC TimeComp0
	LDA TimeComp0
	BNE PlayerBoundary
Timer2	
	LDA TimeComp1
	BEQ Leveling
	DEC TimeComp1
	LDA #50
	STA TimeComp0
	LDA TimeComp1
	BNE PlayerBoundary
Leveling
	LDA #50
	STA TimeComp0
	STA TimeComp1
	LDA YPosForPlay
	STA COLUPF
	LDA LevelingBol
	BEQ BallDifficulty
PlayerSize
	LDA PlayerHeight
	BEQ PlayerBoundary
	DEC LevelingBol
	DEC PlayerHeight
	JMP PlayerBoundary
BallDifficulty
	LDA #2
	STA LevelingBol
	DEC BallSpeed
PlayerBoundary;checks when player hits wall
	LDA #%10000000
	BIT CXP0FB
	BEQ PlayerMovement
	LDA PlayerDirectionBool
	BEQ MoveDown
	INC YPosForPlay
	INC YPosForPlay
	JMP PlayerMovement
MoveDown;for when player needs to be offset
	DEC YPosForPlay
	DEC YPosForPlay
PlayerMovement;player movement up and down
	LDA #%00100000;down
	BIT SWCHA
	BNE NotDown
	LDA #1
	STA PlayerDirectionBool
	LDA #%10000000
	BIT CXP0FB
	BNE SkipMoveUp
	DEC YPosForPlay
	JMP CheckCollision
NotDown
	LDA #%00010000;up
	BIT SWCHA
	BNE SkipMove
	LDA #0
	STA PlayerDirectionBool
	LDA #%10000000
	BIT CXP0FB
	BNE SkipMoveDown
	INC YPosForPlay
	JMP CheckCollision
SkipMove
	LDA #%00000000  ;put value of 0 in the left nibble (stop)
	STA HMP0	;set the move for player 0
	LDA #37
	JMP CheckCollision
SkipMoveUp;player offsets
	INC YPosForPlay
	INC YPosForPlay
	INC YPosForPlay
	JMP CheckCollision
SkipMoveDown
	DEC YPosForPlay
	DEC YPosForPlay
	DEC YPosForPlay
	JMP CheckCollision
CheckCollision;ball and missle
	LDA #%01000000
	BIT CXM1FB
	BEQ WallFix
	LDA WallCollisionBool
	BNE CheckBallCol
	LDY WallDirectionBool
	CPY #0
	BEQ GameOverStart
	LDA #1
	STA WallCollisionBool
	LDA #%00000000  
	STA HMP0
	LDA #$F0
	STA HMBL
	LDA #0
	STA WallDirectionBool
	JMP CheckBallCol
WallFix
	LDA #0
	STA WallCollisionBool
CheckBallCol;ball and player
	LDA #%01000000
	BIT CXP0FB
	BEQ BallVert
	LDA #1
	STA WallDirectionBool
	LDA #%00010000
	STA HMBL
BallVert;checks which direction o move ball verticaly
	LDA #%10000000
	BIT CXBLPF
	BEQ CheckBallMove
	LDA BallDirection
	BEQ BallDown
	JMP BallUp
BallUp
	LDA #0
	STA BallDirection
	INC YPosForBall
	JMP CheckBallMove
BallDown
	DEC YPosForBall
	;DEC YPosForBall
	LDA #1
	STA BallDirection
CheckBallMove;frame check
	LDA BallFrame
	BEQ BallMoveVert
	DEC BallFrame
	JMP WaitForVblankEnd
BallMoveVert
	LDA BallSpeed
	STA BallFrame
	STA WSYNC
	STA HMOVE
	;ADD YPosForBall, #$F0
	LDA BallDirection
	BNE Down
Up
	INC YPosForBall
	JMP WaitForVblankEnd
Down
	DEC YPosForBall
	JMP WaitForVblankEnd
GameOverStart;checks if youve lost
	LDA #1
	STA GameOverBool
WaitForVblankEnd;waits for the end of verical blank time
	STA CXCLR;clears joystick
	LDA INTIM	
	BNE WaitForVblankEnd	
	LDY #191 	
	LDX #190
	STA WSYNC
	STA VBLANK  	

	JMP ScanLoop
	LDA BallFrame
	BEQ BallMove
	DEC BallFrame
	STA WSYNC
	JMP ScanLoop
BallMove;ball frame check
	LDA BallSpeed
	STA BallFrame
	STA WSYNC
	STA HMOVE 	

;main scanline loop...
ScanLoop ;start of the scan loop aka the kernel...
	LDA GameOverBool
	BNE GameOver
	
	STA WSYNC 	;syncs buffers
	LDA PlayerBuffer
	STA GRP0
	LDA BallBuffer
	STA ENABL
	LDA PlayFieldBuffer
	STA PF0
	STA PF1
	STA PF2



CheckTopWall
	CPY #5
	BEQ FillWall
	CPX #5
	BEQ FillWall
CheckBottomWall
	CPY #190
	BEQ FillWall
	CPX #190
	BEQ FillWall
	LDA #0
	STA PlayFieldBuffer
	JMP KernelPt2
FillWall
	LDA #%11111111
	STA PlayFieldBuffer
KernelPt2
	;the attempt at making a better kernel...
	;STA WSYNC
StartPlayer
	LDA #0
	STA PlayerBuffer
CheckActivatePlayer
	CPY YPosForPlay
	BEQ ActivatePlayer
	CPX YPosForPlay
	BNE SkipActivatePlayer
	STA WSYNC
ActivatePlayer
	LDA PlayerHeight
	STA VisiblePlayerLine 
SkipActivatePlayer
	LDA VisiblePlayerLine	;check the visible player line...
	BEQ FinishPlayer		;skip the drawing if its zero...
IsPlayerOn	
	;LDA PlayerGraphic-1,X	;otherwise, load the correct line from player graphic;unused graphic code
				;section below... it's off by 1 though, since at zero
				;we stop drawing
	LDA #%11111111
	STA PlayerBuffer		;put that line as player graphic
	DEC VisiblePlayerLine 	;and decrement the line count
FinishPlayer
	LDA #0
	STA BallBuffer
	;JMP FinishBall
CheckActivateBall
	CPY YPosForBall
	BEQ ActivateBall
	CPX YPosForBall
	BNE SkipActivateBall
ActivateBall
	LDA #6
	STA VisibleBallLine
SkipActivateBall
	LDA VisibleBallLine	;check the visible player line...
	BEQ FinishBall		;skip the drawing if its zero...
IsBallOn	
	LDA #2 
	STA BallBuffer		;put that line as player graphic
	DEC VisibleBallLine 	;and decrement the line count
FinishBall
;decrement scanline counter
	DEX
	DEX
	DEY
	BEQ EndScanLoop
	DEY
	BNE ScanLoop	;lather rinse repeat
	JMP EndScanLoop
	
GameOver;game over kernel
	STA WSYNC 
	LDA #0
	STA ENABL
	STA PF0
	STA PF1
	STA PF2
	LDA PlayerBuffer
	STA GRP0
	
StartWords
	LDA #0
	STA PlayerBuffer
CheckActivateWords
	CPY #170
	BNE SkipActivateG
ActivateG
	LDA #78
	STA VisiblePlayerLine 
SkipActivateG
	LDX VisiblePlayerLine	;check the visible player line...
	BEQ FinishLetter		;skip the drawing if its zero...
IsGOn	
	LDA GGraphic-1,X	;otherwise, load the correct line from player graphic
				;section below... it's off by 1 though, since at zero
				;we stop drawing
	STA PlayerBuffer		;put that line as player graphic
	DEC VisiblePlayerLine 	;and decrement the line count
	JMP FinishLetter
FinishLetter
	
	
	DEY
	BNE GameOver


;overscan same as last time
EndScanLoop
	LDA #2		
	STA WSYNC  	
	STA VBLANK 	
	LDX #30		
OverScanWait
	STA WSYNC
	DEX
	BNE OverScanWait
	JMP  MainLoop      
	
	
GGraphic;player graphic
	.byte #%11000110
	.byte #%11001100
	.byte #%11011000
	.byte #%11110000
	.byte #%11111111
	.byte #%11000011
	.byte #%11000011
	.byte #%11111111
	.byte #%11111111
	.byte #%00000000;
	.byte #%11111111
	.byte #%11111111
	.byte #%11000000
	.byte #%11111111
	.byte #%11111111
	.byte #%11000000
	.byte #%11111111
	.byte #%11111111
	.byte #%00000000;
	.byte #%00011000
	.byte #%00011000
	.byte #%00111100
	.byte #%01100110
	.byte #%01100110
	.byte #%01100110
	.byte #%01100110
	.byte #%11000011
	.byte #%00000000;;;;;;;;;;;;;;;;;;;;;ending point of swapping
	.byte #%11111111
	.byte #%11000011
	.byte #%11000011
	.byte #%11000011
	.byte #%11000011
	.byte #%11000011
	.byte #%11000011
	.byte #%11111111
	.byte #%00000000
	.byte #%00000000
	.byte #%00000000
	.byte #%00000000
	.byte #%00000000
	.byte #%00000000
	.byte #%00000000;;;;;;;;;;;
	.byte #%11111111
	.byte #%11111111
	.byte #%11000000
	.byte #%11111111
	.byte #%11111111
	.byte #%11000000
	.byte #%11111111
	.byte #%11111111
	.byte #%00000000
	.byte #%11011011
	.byte #%11011011
	.byte #%11011011
	.byte #%11011011
	.byte #%11011011
	.byte #%11011011
	.byte #%11011011
	.byte #%11111111
	.byte #%00000000
	.byte #%11000011
	.byte #%11000011
	.byte #%11000011
	.byte #%11000011
	.byte #%11111111
	.byte #%11000011
	.byte #%11000011
	.byte #%11111111
	.byte #%00000000
	.byte #%11111111
	.byte #%11000011
	.byte #%11000011
	.byte #%11111111
	.byte #%11111111
	.byte #%11000000
	.byte #%11000000
	.byte #%11111111
	.byte #%00000000;
	
	
	
	
	org $FFFC
	.word Start
	.word Start