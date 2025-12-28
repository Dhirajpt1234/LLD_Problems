# Tic Tac Toe - UML Class Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              ENUMS & INTERFACES                              │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────┐
│    <<enum>>      │
│     Symbol       │
├──────────────────┤
│ X                │
│ O                │
│ EMPTY            │
└──────────────────┘

┌──────────────────────────┐
│   <<interface>>          │
│    BoardGames            │
├──────────────────────────┤
│ + play(): void           │
└──────────────────────────┘

┌──────────────────────────┐
│   <<interface>>          │
│  PlayerStrategy          │
├──────────────────────────┤
│ + makeMove(Board): Pos   │
└──────────────────────────┘

┌──────────────────────────┐
│   <<interface>>          │
│    GameState             │
├──────────────────────────┤
│ + next(Context, P, bool) │
│ + isGameOver(): boolean  │
└──────────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                            UTILITY CLASSES                                   │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────┐
│     Position         │
├──────────────────────┤
│ + row: int           │
│ + col: int           │
├──────────────────────┤
│ + Position(int, int) │
│ + toString(): String │
└──────────────────────┘

┌──────────────────────────────────┐
│          Player                  │
├──────────────────────────────────┤
│ - symbol: Symbol                 │
│ - playerStrategy: PlayerStrategy │
├──────────────────────────────────┤
│ + Player(Symbol, Strategy)       │
│ + getSymbol(): Symbol            │
│ + getPlayerStrategy(): Strategy  │
└──────────────────────────────────┘

┌────────────────────────────────────────┐
│            Board                       │
├────────────────────────────────────────┤
│ - rows: int                            │
│ - cols: int                            │
│ - grid: Symbol[][]                     │
├────────────────────────────────────────┤
│ + Board(int, int)                      │
│ + isValidMove(Position): boolean       │
│ + makeMove(Position, Symbol): void     │
│ + checkGameState(Context, Player): void│
│ + printBoard(): void                   │
│ - isBoardFull(): boolean               │
│ - isWinningLine(Symbol[]): boolean     │
│ - printSeparator(): void               │
└────────────────────────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                         GAME CONTROLLER                                      │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────┐
│        TicTacToeGame                     │
│    implements BoardGames                 │
├──────────────────────────────────────────┤
│ - board: Board                           │
│ - playerX: Player                        │
│ - playerO: Player                        │
│ - currentPlayer: Player                  │
│ - gameContext: GameContext               │
├──────────────────────────────────────────┤
│ + TicTacToeGame(Strategy, Strategy, ...) │
│ + play(): void                           │
│ - switchPlayer(): void                   │
│ - announceResult(): void                 │
└──────────────────────────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                      GAME STATE PATTERN                                      │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────┐
│      GameContext             │
├──────────────────────────────┤
│ - currentGameState: GameState│
├──────────────────────────────┤
│ + GameContext()              │
│ + getCurrentGameState()      │
│ + setCurrentGameState()      │
│ + next(Player, boolean)      │
│ + isGameOver(): boolean      │
└──────────────────────────────┘
         │
         │ uses
         ▼
    ┌────────────────────┐
    │  <<interface>>     │
    │   GameState        │
    └────────────────────┘
         ▲
         │ implements
         │
    ┌────┴────┬──────────┬──────────┐
    │          │          │          │
    ▼          ▼          ▼          ▼
┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐
│XTurnSt │ │OTurnSt │ │XWonSt  │ │OWonSt  │
│  ate   │ │  ate   │ │  ate   │ │  ate   │
├────────┤ ├────────┤ ├────────┤ ├────────┤
│+ next()│ │+ next()│ │+ next()│ │+ next()│
│+ isGO()│ │+ isGO()│ │+ isGO()│ │+ isGO()│
└────────┘ └────────┘ └────────┘ └────────┘
  (turn)     (turn)    (win=T)    (win=T)


┌─────────────────────────────────────────────────────────────────────────────┐
│                      PLAYER STRATEGY PATTERN                                 │
└─────────────────────────────────────────────────────────────────────────────┘

    ┌──────────────────────┐
    │ <<interface>>        │
    │  PlayerStrategy      │
    └──────────────────────┘
             ▲
             │ implements
             │
    ┌────────┴──────────┐
    │                   │
    ▼                   ▼
┌──────────────┐  ┌──────────────┐
│HumanPlayer   │  │(AI Strategy) │
│Strategy      │  │(Future)      │
├──────────────┤  └──────────────┘
│- playerName  │
│- scanner     │
├──────────────┤
│+ makeMove()  │
└──────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                      RELATIONSHIPS SUMMARY                                   │
└─────────────────────────────────────────────────────────────────────────────┘

TicTacToeGame
  ├─ has 1 Board
  ├─ has 2 Players (playerX, playerO)
  │   └─ each Player has 1 PlayerStrategy
  └─ has 1 GameContext
      └─ manages GameState (State Pattern)

Board
  ├─ uses Symbol (enum)
  └─ uses Position

GameContext
  ├─ manages GameState transitions
  └─ states: XTurnState, OTurnState, XWonState, OWonState

PlayerStrategy
  └─ implemented by: HumanPlayerStrategy


┌─────────────────────────────────────────────────────────────────────────────┐
│                      DESIGN PATTERNS USED                                    │
└─────────────────────────────────────────────────────────────────────────────┘

1. STATE PATTERN
   - GameState interface with concrete states (XTurnState, OTurnState, etc.)
   - GameContext manages state transitions
   - Encapsulates game flow logic

2. STRATEGY PATTERN
   - PlayerStrategy interface for different player types
   - HumanPlayerStrategy for human input
   - Extensible for AI strategies

3. MVC-like ARCHITECTURE
   - Controller: TicTacToeGame
   - Model: Board, Player, GameContext
   - View: printBoard() methods

