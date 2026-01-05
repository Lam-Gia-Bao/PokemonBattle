# PokemonBattle
Đồ án lập trình AI: Game Pokemon Battle

## So sánh Minimax vs Alpha-Beta (số liệu thực từ game)

Đo bằng chương trình benchmark trong `game/BenchmarkSearch.java` với các team mặc định (Greninja vs Charizard) trên máy hiện tại.

| Depth | Minimax Memory (KB) | Minimax Time (ms) | Alpha-Beta Memory (KB) | Alpha-Beta Time (ms) |
|-------|----------------------|-------------------|-------------------------|----------------------|
| 1     | 2262                 | 6                 | 1413                    | <1                   |
| 2     | 1567                 | <1                | 1559                    | <1                   |
| 3     | 1617                 | <1                | 1616                    | <1                   |
| 4     | 1824                 | <1                | 1748                    | <1                   |
| 5     | 2680                 | 2                 | 2061                    | <1                   |
| 6     | 5234                 | 11                | 2681                    | <1                   |

Ghi chú: Thời gian 0 ms nghĩa là dưới 1 ms (sai số đo lường hệ thống). Alpha-Beta giúp giảm thời gian duyệt rõ rệt ở độ sâu lớn.
