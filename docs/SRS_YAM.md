# Đặc tả yêu cầu phần mềm YAM

Yet Another Minesweeper

Phiên bản 1.0 • Ngày lập 26 tháng 10 năm 2026 • Ngôn ngữ tiếng Việt

Tài liệu này quy định hành vi cần đạt của trò chơi dò mìn YAM và tiêu chí để nhóm phát triển, kiểm thử viên và giảng viên đánh giá sản phẩm. Phạm vi bao gồm trò chơi Java Swing chạy cục bộ, ba mức độ khó, các thao tác mở ô và cắm cờ, mở vùng bằng BFS hoặc DFS, bảng thành tích lưu bằng tệp và các ràng buộc cấu trúc dữ liệu của học phần CSD201.

Đường cơ sở yêu cầu gồm README và hợp đồng trong mã nguồn của inthrephc/yam, kết hợp tài liệu phân công nhóm. Các yêu cầu ghi “Xác lập” được truy nguyên đến nguồn; các mục ghi “Đề xuất” là quy tắc bổ sung để việc triển khai và nghiệm thu có kết quả xác định. Trạng thái phê duyệt của bản SRS là chờ nhóm rà soát; trạng thái này không phải xác nhận phần mềm đã đáp ứng các yêu cầu.

## 1 Giới thiệu và quản lý tài liệu

### Mục đích và đối tượng sử dụng

SRS là căn cứ thống nhất giữa năm thành viên về những gì phần mềm phải làm, dữ liệu phải lưu, các trường hợp biên và điều kiện hoàn thành. Người phát triển dùng mã yêu cầu để triển khai; người kiểm thử dùng tiêu chí nghiệm thu và bảng AT; người tích hợp dùng ma trận trách nhiệm và phụ thuộc; giảng viên dùng các ràng buộc học thuật để đánh giá việc áp dụng cấu trúc dữ liệu và thuật toán.

Các tên lớp, phương thức và hằng số được giữ theo repo nhằm truy nguyên. Chúng là ràng buộc thiết kế khi nguồn đã quy định, không thay thế yêu cầu về hành vi quan sát được. Tài liệu không yêu cầu người chơi hiểu BFS, DFS hay định dạng tệp để sử dụng trò chơi.

### Quy ước yêu cầu

| Ký hiệu | Ý nghĩa |
| --- | --- |
| FR | Yêu cầu chức năng có thể quan sát hoặc kiểm tra ở mức API |
| BR | Quy tắc nghiệp vụ và bất biến dữ liệu |
| UC | Kịch bản tương tác giữa người chơi và ứng dụng |
| DC | Ràng buộc thiết kế hoặc kỹ thuật bắt buộc của đề tài |
| NFR | Yêu cầu chất lượng và điều kiện vận hành |
| AT | Ca kiểm thử nghiệm thu dự kiến |
| OI | Điểm cần nhóm xác nhận trước khi chốt đường cơ sở |
| Xác lập | Có căn cứ rõ trong README, Javadoc, mã hoặc tài liệu phân công |
| Đề xuất | Bổ sung hợp lý, cần nhóm chấp thuận trước khi dùng làm điều kiện bắt buộc |

“Phải” biểu thị nghĩa vụ kiểm thử được; “nên” là khuyến nghị. Mức P0 là điều kiện chơi đúng hoặc ràng buộc học phần; P1 là chức năng cần có trong bản đầy đủ; P2 là cải thiện chất lượng. Mặc định chức năng luật chơi và DC là P0, giao diện phụ trợ và bảng xếp hạng là P1. Mỗi FR có phần nghiệm thu; việc xuất hiện của một FR không hàm ý ca kiểm thử đã được chạy.

### Danh mục nguồn và phiên bản

Snapshot GitHub được đọc trên nhánh master tại commit 7456c382fa18564113509eafaf5f326c97b5894e, truy cập ngày 26 tháng 10 năm 2026. Các đường dẫn G1 đến G8 được cố định theo commit này để tránh thay đổi khi nhánh tiếp tục phát triển.

| Mã nguồn | Nội dung và vị trí |
| --- | --- |
| G1 | README.md mô tả trò chơi, thuật toán và cách chạy |
| G2 | src/yam/model và src/yam/logic/MinePlacer.java quy định ô, bàn, độ khó và đặt mìn |
| G3 | src/yam/logic/Game.java và FloodFill.java quy định luật điều phối ván và mở vùng |
| G4 | src/yam/Main.java và src/yam/ui quy định Swing, chuột, menu, hiển thị và hộp thoại |
| G5 | src/yam/score quy định kỷ lục, so sánh, lọc và lưu đọc tệp |
| G6 | src/yam/util/Constants.java chứa giá trị cấu hình chung |
| G7 | src/yam/ds quy định danh sách liên kết, ngăn xếp, hàng đợi và iterator |
| G8 | nbproject/project.properties, build.xml, manifest.mf quy định build và điểm vào |
| N1 | Tài liệu phân công do nhóm cung cấp gồm 5 mục từ Bảng phân công đến Các mốc tích hợp |

Đường dẫn truy cập chi tiết nằm ở Phụ lục nguồn. Khi nguồn chưa nói rõ một hành vi, SRS đặt hành vi đó trong mục đề xuất hoặc OI. Nếu nguồn mâu thuẫn, nhóm phải quyết định và cập nhật đồng thời SRS, Javadoc, README và kiểm thử liên quan.

### Đường cơ sở và phạm vi xác minh

Snapshot có 22 tệp Java. Model, MinePlacer và cấu trúc dữ liệu có mã thực thi; các enum, interface và hằng số đã có định nghĩa; Game, FloodFill, score và một số lớp giao diện còn TODO hoặc phương thức chưa triển khai. Vì vậy đây là đặc tả sản phẩm đích và kế hoạch nghiệm thu, không phải báo cáo xác nhận sản phẩm hiện đã chơi được. Việc đọc mã không tương đương thực thi build hoặc chạy GUI. Báo cáo kiểm thử thực tế phải ghi riêng môi trường, phiên bản commit, kết quả và bằng chứng.

## 2 Mô tả tổng quan sản phẩm

### Bối cảnh và mục tiêu

YAM là trò chơi dò mìn một người trên máy tính, phục vụ đồ án CSD201 về cấu trúc dữ liệu và giải thuật. Người chơi phải mở toàn bộ ô không có mìn, sử dụng số mìn lân cận để suy luận và cắm cờ những ô nghi ngờ. Trò chơi đo thời gian thắng và giữ tối đa 10 thành tích nhanh nhất cho từng độ khó.

Mục tiêu sản phẩm là hoàn thành một ván đúng luật, phản hồi rõ ràng các thao tác chuột, bảo vệ lần mở đầu tiên và lưu thành tích qua các lần khởi động. Mục tiêu học thuật là sử dụng các cấu trúc tự cài đặt và chứng minh BFS, DFS, Fisher–Yates, chèn có thứ tự hoạt động đúng.

### Phạm vi trong phiên bản

| Trong phạm vi | Kết quả cần có |
| --- | --- |
| Ván chơi cục bộ | Khởi tạo, mở ô, mở vùng, cắm và bỏ cờ, chord, thắng, thua và chơi lại |
| Ba độ khó cố định | Beginner, Intermediate, Expert với kích thước và số mìn xác định |
| Theo dõi ván | Trạng thái khuôn mặt, số mìn còn lại và đồng hồ ba chữ số |
| Thành tích | Nhập tên sau thắng đủ điều kiện, top 10 riêng từng độ khó, lưu đọc leaderboard.txt |
| Thông tin hỗ trợ | Hướng dẫn chơi, giới thiệu, menu và phím F2 |
| Kiểm chứng thuật toán | Seed cho kiểm thử, giao diện debug, sandbox từng thành viên |

Không thuộc phạm vi đường cơ sở này: tài khoản và đăng nhập, chơi nhiều người, máy chủ hoặc xếp hạng trực tuyến, đồng bộ đám mây, giao dịch thanh toán, trình giải tự động hoặc gợi ý, hoàn tác, tạm dừng, lưu tiếp ván dở, bản đồ tùy chỉnh trên GUI và lựa chọn BFS/DFS từ menu. Constructor Board linh hoạt phục vụ mô hình và kiểm thử không tự tạo ra tính năng bàn tùy chỉnh cho người dùng.

### Tác nhân và hệ thống liên quan

Người chơi là tác nhân chính: chọn độ khó, thao tác bàn, xem thành tích và nhập tên. Hệ thống tệp cục bộ là tác nhân hỗ trợ lưu đọc dữ liệu. Đồng hồ hệ thống cung cấp mốc đo thời gian và ngày ghi thành tích. Người kiểm thử hoặc giảng viên gọi API không GUI, sử dụng seed và kiểm tra ràng buộc học thuật. Nhóm phát triển là bên duy trì mã, không phải vai trò có quyền quản trị trong ứng dụng.

Ứng dụng không có API mạng, cơ sở dữ liệu ngoài hoặc yêu cầu xác thực. Tên trong bảng thành tích là nhãn hiển thị cục bộ, không phải danh tính được xác minh.

### Môi trường và giả định

Môi trường xây dựng theo G1 và G8 là JDK 21 trở lên, dự án NetBeans Java with Ant, mã UTF-8 và lớp chính yam.Main. Cách chạy bằng dòng lệnh là ant clean jar rồi java -jar dist/yam.jar. G8 đặt source và target bằng 21; hỗ trợ JDK mới hơn chỉ được coi là đạt sau khi build và chạy thử trên phiên bản cụ thể.

Máy chạy phải hỗ trợ Java Swing và thiết bị trỏ với thao tác trái/phải; chord còn có đường thay thế là nhấn trái lên ô số đã mở. Thư mục làm việc quyết định vị trí leaderboard.txt. Quyền ghi tại đó là điều kiện lưu bền vững; việc không có quyền ghi phải được xử lý theo chính sách lỗi dữ liệu ở mục 8. Màn hình đủ rộng để hiển thị lưới Expert 900 × 480 px cộng khung và thanh menu là giả định môi trường, không phải kết quả kiểm chứng đa nền tảng.

## 3 Quy tắc nghiệp vụ và trạng thái

### Các mức độ khó

| Giá trị enum | Nhãn | Hàng | Cột | Số ô | Mìn | Ô an toàn phải mở |
| --- | --- | --- | --- | --- | --- | --- |
| BEGINNER | Beginner | 9 | 9 | 81 | 10 | 71 |
| INTERMEDIATE | Intermediate | 16 | 16 | 256 | 40 | 216 |
| EXPERT | Expert | 16 | 30 | 480 | 99 | 381 |

Nguồn G1, G2. Tọa độ trong mã bắt đầu từ 0; hàng tăng từ trên xuống, cột tăng từ trái sang phải. Người dùng không cần nhập tọa độ.

### Các bất biến

| Mã | Quy tắc bắt buộc | Nguồn |
| --- | --- | --- |
| BR-01 | Chỉ thắng khi mọi ô an toàn đều mở. Cắm đủ số cờ hoặc cắm đúng tất cả mìn chưa đủ để thắng. | G1 G2 G3 |
| BR-02 | Ô mang cờ không được mở trực tiếp hoặc qua flood fill và chord. | G1 G3 N1 |
| BR-03 | READY chưa đặt mìn. Lần reveal hợp lệ đầu tiên mới đặt mìn và bắt đầu đồng hồ. | G2 G3 |
| BR-04 | Đúng số mìn cấu hình, không trùng vị trí, không đặt vào vùng được bảo vệ. | G2 N1 |
| BR-05 | Số trên ô an toàn bằng số mìn trong tối đa 8 ô xung quanh, không tính chính ô đó và không nối qua mép bàn. | G1 G2 |
| BR-06 | Bộ đếm bằng tổng mìn trừ số cờ, có thể âm; số cờ không phải số mìn được xác nhận. | G1 G3 |
| BR-07 | Hành động chơi không thay đổi ván khi WON hoặc LOST. Chỉ tạo Game mới để chơi tiếp. | G3 N1 |
| BR-08 | revealedCount chỉ tăng một lần khi Board.revealCell chuyển một ô ẩn thành mở; đường chơi chỉ dùng phép này cho ô an toàn. | G2 G3 N1 |
| BR-09 | Lộ mìn lúc thua không tăng bộ đếm ô an toàn và không dẫn đến chuyển LOST sang WON. | G2 G3 |
| BR-10 | Khi thắng tự cắm cờ các mìn còn ẩn và đặt flagsPlaced bằng số mìn để bộ đếm về 0. | G1 G2 G3 |
| BR-11 | Thành tích được so bằng thời gian tăng dần, sau đó ngày tăng dần; cùng cả hai giữ thứ tự chèn. | G5 G7 N1 |
| BR-12 | Mỗi độ khó giữ riêng tối đa 10 thành tích; chỉ ván thắng mới phát sinh thành tích. | G1 G5 G6 |

Board.revealCell không tự kiểm tra ô có mìn. Đây là hợp đồng tầng thấp; Game và FloodFill phải kiểm tra trước. Board.allSafeCellsRevealed dựa trên revealedCount, nên gọi sai tầng có thể phá điều kiện thắng dù hàm vẫn trả kết quả theo mã.

### Máy trạng thái ván

| Trạng thái đầu | Sự kiện và điều kiện | Trạng thái sau | Hiệu ứng |
| --- | --- | --- | --- |
| READY | Cắm hoặc bỏ cờ hợp lệ | READY | Đổi cờ và bộ đếm; thời gian bằng 0; chưa đặt mìn |
| READY | Reveal ngoài bàn hoặc ô mang cờ | READY | Bỏ qua, không tiêu thụ lần mở đầu |
| READY | Reveal hợp lệ trên ô ẩn | PLAYING | Đặt mìn an toàn, tính số, ghi giờ bắt đầu, mở ô hoặc vùng |
| PLAYING | Reveal an toàn, chưa đủ ô | PLAYING | Mở ô hoặc vùng và cập nhật giao diện |
| PLAYING | Chord đủ cờ và mọi ô mở đều an toàn | PLAYING hoặc WON | Mở các hàng xóm ẩn; kiểm tra thắng sau thao tác |
| PLAYING | Reveal hoặc chord trúng mìn | LOST | Đánh dấu ô nổ, lộ các mìn chưa cắm cờ, dừng giờ |
| PLAYING | Tất cả ô an toàn đã mở | WON | Chốt giờ, tự cắm cờ mìn và xét thành tích |
| WON hoặc LOST | Reveal, toggleFlag hoặc chord | Giữ nguyên | Không đổi ô, bộ đếm hoặc thời gian đã chốt |
| Bất kỳ | New, F2, nút mặt hoặc chọn độ khó | READY của Game mới | Xóa dữ liệu ván cũ, tạo bàn mới, đặt lại bộ đếm và cờ xử lý kết thúc |

Nếu lần mở đầu làm lộ toàn bộ ô an toàn trên một bàn kiểm thử đặc biệt, PLAYING có thể chuyển WON ngay trong cùng lời gọi. Ba preset vẫn phải đi qua cùng quy trình kiểm tra. Đồng hồ đạt 999 chỉ bão hòa giá trị, không tự kết thúc ván.

### Trạng thái ô

CellState gồm HIDDEN, FLAGGED và REVEALED. toggleFlag chỉ chuyển HIDDEN qua FLAGGED và ngược lại; thao tác trên REVEALED không làm đổi trạng thái. reveal chỉ chuyển HIDDEN sang REVEALED. Thuộc tính mine, adjacentMines và exploded là dữ liệu bổ sung; exploded không phải trạng thái thứ tư.

Khi thua, cờ đúng vẫn hiện cờ; cờ trên ô an toàn hiện dấu cờ sai; mìn chưa cắm cờ hiện mìn; ô gây nổ phải khác về màu nền. “Hiện toàn bộ mìn” ở G1 được hiểu là cho biết mọi vị trí mìn bằng ký hiệu mìn hoặc cờ đúng, phù hợp G2 và G4, không buộc xóa cờ đúng.

## 4 Yêu cầu chức năng

Các FR dưới đây mô tả hành vi mục tiêu. Những mệnh đề đề xuất chỉ trở thành điều kiện nghiệm thu bắt buộc khi được nhóm chấp thuận ở mục 13.

### Luật chơi và bàn cờ

Các yêu cầu sau mô tả hành vi đích cần nghiệm thu. “Xác lập” là yêu cầu được thể hiện trực tiếp trong nguồn; nhãn này không có nghĩa mã nguồn đã triển khai xong hoặc đã vượt kiểm thử. Các lớp Game và FloodFill tại mốc đối chiếu còn TODO. G1 là README; G2 là model và MinePlacer; G3 là Game và FloodFill; N1 là tài liệu phân công do nhóm cung cấp.

#### FR 01 Khởi tạo ván mới

**Phân loại và nguồn:** Xác lập; [G1], [G3], [N1].

**Mục đích:** Cho phép người chơi bắt đầu lại một ván độc lập.

**Yêu cầu:** Khi người chơi chọn Game → New hoặc nhấn nút khuôn mặt, hệ thống phải tạo đối tượng Game mới với độ khó đang được chọn. Bàn cờ mới phải có toàn bộ ô HIDDEN, số cờ bằng 0, trạng thái READY và thời gian bằng 0. Hệ thống chỉ được sinh vị trí mìn sau thao tác mở ô hợp lệ đầu tiên. Dữ liệu tạm của ván cũ, gồm ô mở, cờ, ô nổ và mốc thời gian, không được truyền sang ván mới.

**Điều kiện và ngoại lệ:** Chức năng áp dụng khi ván cũ ở READY, PLAYING, WON hoặc LOST. Ván đang chơi bị thay thế; tài liệu nguồn không yêu cầu hộp thoại xác nhận.

**Nghiệm thu:** Chơi một ván đã đặt cờ và mở ô, tạo ván mới qua từng cách kích hoạt; kiểm tra toàn bộ trạng thái khởi đầu và độ khó được giữ đúng.

#### FR 02 Chọn độ khó

**Phân loại và nguồn:** Xác lập; [G1], [G2], [N1].

**Mục đích:** Cung cấp ba cấu hình bàn cờ thống nhất.

**Yêu cầu:** Hệ thống phải cho chọn Beginner với 9 hàng, 9 cột và 10 mìn; Intermediate với 16 hàng, 16 cột và 40 mìn; Expert với 16 hàng, 30 cột và 99 mìn. Chọn một cấu hình phải bắt đầu ván mới theo cấu hình đó. Số ô an toàn tương ứng phải là 71, 216 và 381. Giao diện phải cập nhật kích thước lưới và cửa sổ cho phù hợp.

**Điều kiện và ngoại lệ:** Không suy ra chức năng nhập kích thước tùy ý từ constructor tổng quát của Board. Thứ tự hàng và cột của Expert phải giữ đúng 16×30.

**Nghiệm thu:** Chọn lần lượt ba mức từ menu; đếm số ô, kiểm tra số mìn cấu hình, bộ đếm ban đầu và trạng thái READY sau mỗi lần chọn.

#### FR 03 Chờ thao tác mở đầu tiên

**Phân loại và nguồn:** Xác lập; [G2], [G3], [N1].

**Mục đích:** Đảm bảo thời gian và bản đồ mìn bắt đầu đúng thời điểm.

**Yêu cầu:** Ván mới phải ở READY và chưa có mìn được đặt. Chỉ một thao tác reveal trên ô HIDDEN, không cắm cờ và nằm trong bàn mới được đặt mìn, tính các số hàng xóm, ghi thời điểm bắt đầu và chuyển sang PLAYING. Thao tác mở đầu tiên sau đó phải được xử lý theo luật mở ô thông thường.

**Điều kiện và ngoại lệ:** Cắm cờ, bỏ cờ, click ô có cờ hoặc click tọa độ sai trong READY không được đặt mìn hoặc chạy đồng hồ. Chord trong READY không có tác dụng.

**Nghiệm thu:** Thực hiện các thao tác ngoại lệ trước click hợp lệ; timer vẫn 0, trạng thái vẫn READY. Sau click hợp lệ, chỉ có một lần đặt mìn và timer bắt đầu.

#### FR 04 Bảo vệ lần mở đầu

**Phân loại và nguồn:** Xác lập; [G1], [G2], [N1].

**Mục đích:** Ngăn người chơi thua ngay trong lần mở ô đầu tiên.

**Yêu cầu:** Hệ thống phải luôn giữ ô được mở đầu tiên không có mìn. Với SAFE_FIRST_CLICK_NEIGHBORS=true và đủ chỗ đặt mìn, các ô hàng xóm của ô đó cũng phải không có mìn. Vùng bảo vệ gồm các ô thực sự nằm trong bàn có chênh lệch hàng và cột so với ô click không quá 1.

**Điều kiện và ngoại lệ:** Nếu số ô ngoài vùng bảo vệ nhỏ hơn mineCount, hệ thống phải chỉ bảo vệ ô click và vẫn đặt đủ mìn. Ba mức chuẩn đều đủ chỗ để bảo vệ hàng xóm.

**Nghiệm thu:** Thử click đầu ở góc, cạnh và giữa trên ba mức; ô click và các hàng xóm không có mìn. Với bàn nội bộ 2×2 có 3 mìn, giữ đúng ô click an toàn và đặt đủ 3 mìn.

#### FR 05 Sinh mìn đúng số lượng

**Phân loại và nguồn:** Xác lập; [G1], [G2], [N1].

**Mục đích:** Tạo bản đồ mìn hợp lệ và hỗ trợ tái lập kiểm thử.

**Yêu cầu:** Trên bàn mới, MinePlacer phải đặt đúng mineCount mìn tại các ô khác nhau, ngoài vùng bảo vệ có hiệu lực. Thuật toán phải chọn từ danh sách ứng viên bằng partial Fisher–Yates với java.util.Random. Sau đặt mìn, Game phải yêu cầu Board tính số mìn lân cận trước khi mở ô.

**Điều kiện và ngoại lệ:** Đặt mìn chỉ diễn ra một lần cho mỗi ván. Cờ đặt trong READY không quyết định ô nào được chọn làm mìn. Constructor nhận Random có seed được dùng cho kiểm thử, không phải tùy chọn giao diện.

**Nghiệm thu:** Chạy 1000 bản đồ Expert với các điểm click hợp lệ; mọi lần có đúng 99 mìn, không trùng, đúng vùng bảo vệ. Cùng seed, preset và click đầu phải tái lập bản đồ.

#### FR 06 Xác định hàng xóm và số ô

**Phân loại và nguồn:** Xác lập; [G1], [G2], [N1].

**Mục đích:** Cung cấp dữ liệu chính xác cho hiển thị số và chord.

**Yêu cầu:** Mỗi ô phải xét tối đa tám vị trí xung quanh, gồm cả đường chéo, và chỉ lấy các vị trí thuộc bàn cờ. Với mỗi ô không có mìn, adjacentMines phải bằng đúng số mìn trong các hàng xóm và nằm trong khoảng 0 đến 8. Đếm cờ hàng xóm phải dựa trên trạng thái FLAGGED hiện thời.

**Điều kiện và ngoại lệ:** Các hàng xóm không nối vòng qua biên. Số trên ô mìn không được dùng làm thông tin luật chơi. Với bàn chuẩn, góc có 3 hàng xóm, cạnh không phải góc có 5 và ô giữa có 8.

**Nghiệm thu:** Dựng bản đồ đã biết vị trí mìn; đối chiếu số của mọi ô an toàn. Cắm và bỏ cờ quanh một số; kết quả đếm cờ thay đổi chính xác.

#### FR 07 Mở một ô có số

**Phân loại và nguồn:** Xác lập; [G1], [G2], [G3].

**Mục đích:** Cho người chơi thu thập thông tin về mìn.

**Yêu cầu:** Khi reveal hợp lệ trỏ đến ô an toàn có adjacentMines từ 1 đến 8, hệ thống phải chuyển ô đó từ HIDDEN sang REVEALED, hiển thị số tương ứng và tăng số ô an toàn đã mở đúng một đơn vị. Ô số phải kết thúc nhánh lan của flood fill; thao tác này không tự mở các ô lân cận ngoài quy tắc chord.

**Điều kiện và ngoại lệ:** Nếu ô là số 0 thì áp dụng FR-11. Nếu ô chứa mìn thì áp dụng FR-16. Việc kiểm tra thắng phải diễn ra sau khi xử lý mở ô.

**Nghiệm thu:** Mở một ô số trên bản đồ kiểm soát; chỉ ô đó đổi trạng thái, revealedCount tăng một và số hiển thị khớp mìn hàng xóm.

#### FR 08 Bảo vệ thao tác mở ô

**Phân loại và nguồn:** Xác lập; [G2], [G3], [N1].

**Mục đích:** Tránh mở nhầm cờ và đếm trùng ô.

**Yêu cầu:** Game.reveal phải bỏ qua tọa độ ngoài bàn, ô FLAGGED và mọi thao tác khi ván đã kết thúc. Ô đã REVEALED phải được chuyển xử lý sang chord, không được mở và tăng bộ đếm lần nữa. Trong luồng chơi, mọi ô an toàn mới mở phải đi qua Board.revealCell để cập nhật revealedCount.

**Điều kiện và ngoại lệ:** Cell.reveal chỉ trả true khi đổi HIDDEN thành REVEALED; khi ô đã mở hoặc có cờ phải trả false và không thay đổi. Các hàm chẩn đoán không được thay đổi trạng thái chơi.

**Nghiệm thu:** Gọi mở lặp cùng ô và mở ô có cờ; bộ đếm không tăng sai. Gọi reveal với hàng âm hoặc bằng số hàng; ván và giao diện vẫn ổn định.

#### FR 09 Cắm và bỏ cờ

**Phân loại và nguồn:** Xác lập; [G1], [G2], [G3], [N1].

**Mục đích:** Cho người chơi đánh dấu ô nghi có mìn.

**Yêu cầu:** Chuột phải trên ô HIDDEN phải chuyển ô thành FLAGGED và tăng flagsPlaced một đơn vị. Chuột phải trên ô FLAGGED phải chuyển ô về HIDDEN và giảm flagsPlaced một đơn vị. Thao tác được phép ở READY và PLAYING, không mở ô và không khởi động ván từ READY.

**Điều kiện và ngoại lệ:** Ô REVEALED không thể cắm cờ. Tọa độ sai và ván WON hoặc LOST không được thay đổi. Nguồn không áp giới hạn số cờ theo số mìn; không tự từ chối cờ chỉ vì bộ đếm mìn bằng 0.

**Nghiệm thu:** Cắm rồi bỏ cờ cùng ô, kiểm tra trạng thái và tổng cờ trở về ban đầu. Thử chuột phải trên ô mở và sau kết thúc; dữ liệu không đổi.

#### FR 10 Cập nhật bộ đếm mìn

**Phân loại và nguồn:** Xác lập; [G1], [G3], [N1].

**Mục đích:** Hiển thị số mìn ước lượng còn chưa được đánh dấu.

**Yêu cầu:** Giá trị logic của bộ đếm phải bằng số mìn cấu hình trừ flagsPlaced. Giá trị được phép âm khi người chơi cắm nhiều cờ hơn số mìn; đây là kết quả hợp lệ và không tự dẫn đến thua. Sau mỗi thay đổi cờ, giao diện phải hiển thị giá trị mới. Sau thắng, tổng cờ được đồng bộ bằng số mìn và bộ đếm phải bằng 0.

**Điều kiện và ngoại lệ:** Bộ đếm không phản ánh có bao nhiêu cờ đúng; không được dùng bộ đếm bằng 0 như điều kiện thắng.

**Nghiệm thu:** Trên Beginner, cắm 12 cờ cho kết quả logic -2. Bỏ một cờ cho -1. Bàn chưa mở hết ô an toàn vẫn chưa thắng dù đặt đúng 10 cờ.

#### FR 11 Tự mở vùng ô trống

**Phân loại và nguồn:** Xác lập; [G1], [G3], [N1].

**Mục đích:** Giảm thao tác khi người chơi mở vùng không giáp mìn.

**Yêu cầu:** Mở ô an toàn có adjacentMines=0 phải tự mở vùng các ô HIDDEN số 0 liên thông theo tám hướng và các ô HIDDEN có số an toàn ở biên có thể đến được từ vùng này. Các ô đã REVEALED trước thao tác không được thêm lại làm cầu lan; các ô vừa mở trong cùng lượt duyệt vẫn được xử lý theo queue hoặc stack. Việc lan chỉ tiếp tục từ ô số 0. Mỗi ô mới mở phải được tính một lần; mìn và ô cắm cờ phải luôn được giữ đóng.

**Điều kiện và ngoại lệ:** Ô cắm cờ, kể cả cờ sai trên ô an toàn, không bị tự gỡ. Flood fill bắt đầu ở ô không HIDDEN hoặc ô có mìn phải trả 0.

**Nghiệm thu:** Dùng bản đồ có vùng 0, ô số biên và cờ chặn; tập ô mở phải đúng vùng dự kiến, không có mìn/cờ bị mở, số trả về bằng số ô mới mở.

#### FR 12 Cung cấp hai thuật toán flood fill

**Phân loại và nguồn:** Xác lập; [G1], [G3], [N1].

**Mục đích:** Đáp ứng mục tiêu môn học về hàng đợi, ngăn xếp và duyệt đồ thị.

**Yêu cầu:** FloodFill phải có revealBfs dùng MyQueue và revealDfs dùng MyStack. Cả hai phải triển khai lặp, không đệ quy, đánh dấu đã thăm bằng reveal ngay trước enqueue hoặc push. Trên cùng trạng thái bàn và cùng ô bắt đầu, hai phiên bản phải mở cùng tập ô và trả cùng số ô mới mở. Game dùng thuật toán theo Constants.USE_BFS_FLOOD_FILL; cấu hình hiện tại chọn BFS.

**Điều kiện và ngoại lệ:** Thứ tự thăm có thể khác nhau. Đây là cấu hình kỹ thuật, không có yêu cầu menu cho người chơi chọn thuật toán.

**Nghiệm thu:** Sao chép trạng thái bàn cho hai phép chạy; so sánh tập tọa độ mở, revealedCount và giá trị trả về, kể cả bàn có cờ.

#### FR 13 Mở nhiều ô bằng chord

**Phân loại và nguồn:** Xác lập; [G1], [G2], [G3], [N1].

**Mục đích:** Cho phép mở nhanh các ô quanh một số đã có đủ cờ.

**Yêu cầu:** Chord phải được kích hoạt bằng chuột giữa hoặc chuột trái trên ô số đã mở. Hệ thống chỉ thực hiện khi ván PLAYING, ô đích REVEALED, số trên ô lớn hơn 0 và số cờ xung quanh bằng số đó. Khi đủ điều kiện, hệ thống phải lần lượt mở các hàng xóm HIDDEN theo luật mở ô và kiểm tra thắng sau thao tác.

**Điều kiện và ngoại lệ:** Chord trên ô ẩn, ô cờ, số 0 hoặc có số cờ thiếu/thừa không đổi bàn. Ô FLAGGED và REVEALED xung quanh được bỏ qua.

**Nghiệm thu:** Trên ô số 2, thử 1, 2 và 3 cờ xung quanh; chỉ trường hợp 2 cờ mở các ô còn ẩn.

#### FR 14 Xử lý cờ sai khi chord

**Phân loại và nguồn:** Xác lập; [G1], [G3], [N1].

**Mục đích:** Giữ nguyên rủi ro của thao tác chord khi suy luận sai.

**Yêu cầu:** Hệ thống phải kiểm tra số lượng cờ để cho phép chord, không mặc định rằng các cờ đều đúng. Nếu một hàng xóm HIDDEN được chord mở là mìn, ván phải chuyển LOST theo FR-16. Vòng mở hàng xóm phải dừng ngay sau phát nổ; không tiếp tục mở các ô sau đó trong thứ tự duyệt.

**Điều kiện và ngoại lệ:** Một số ô an toàn đứng trước mìn trong thứ tự duyệt có thể đã được mở. Không yêu cầu hoàn tác chúng và không yêu cầu chord thất bại như một giao dịch toàn bộ hoặc không có gì.

**Nghiệm thu:** Dựng ô số có đủ số cờ nhưng đặt sai; chord làm thua, có ô exploded và không mở tiếp hàng xóm sau mìn.

#### FR 15 Xác định và xử lý thắng

**Phân loại và nguồn:** Xác lập; [G1], [G2], [G3], [N1].

**Mục đích:** Kết thúc thành công khi người chơi mở đủ ô an toàn.

**Yêu cầu:** Khi ván còn PLAYING và revealedCount bằng tổng ô an toàn, hệ thống phải chuyển WON, chốt thời điểm kết thúc, tự cắm cờ các mìn còn HIDDEN và đồng bộ flagsPlaced bằng mineCount. Giao diện phải thể hiện thắng và bộ đếm 0. Điều kiện thắng không đòi người chơi cắm cờ mìn trước đó.

**Điều kiện và ngoại lệ:** Không kiểm tra thắng sau khi đã LOST. Cờ sai trên ô an toàn sẽ giữ ô đó chưa mở, nên ván chưa thể thắng cho đến khi cờ được gỡ và ô được mở.

**Nghiệm thu:** Mở mọi ô an toàn mà không cắm cờ thủ công; ván thắng đúng tại ô cuối, toàn bộ mìn được đánh dấu, thời gian cố định và bộ đếm bằng 0.

#### FR 16 Xác định và xử lý thua

**Phân loại và nguồn:** Xác lập; [G1], [G2], [G3], [N1].

**Mục đích:** Kết thúc ván khi người chơi kích hoạt mìn.

**Yêu cầu:** Mở mìn trực tiếp hoặc qua chord phải chuyển LOST, chốt thời gian và đánh dấu exploded=true cho mìn gây thua. Hệ thống phải mở các mìn chưa cắm cờ để giao diện hiển thị. Cờ đúng vẫn được giữ và cờ sai phải được đánh dấu phân biệt. Việc mở mìn để trình bày kết quả không được làm tăng bộ đếm ô an toàn đã mở.

**Điều kiện và ngoại lệ:** Không tự mở toàn bộ các ô an toàn chưa mở sau thua. “Hiển thị mìn” không đồng nghĩa mọi ô mìn đều đổi sang REVEALED, vì các mìn có cờ vẫn FLAGGED.

**Nghiệm thu:** Tạo bàn có cờ đúng và sai, mở một mìn; kiểm tra ô nổ, các mìn khác, cờ sai và timer dừng.

#### FR 17 Tính và cố định thời gian

**Phân loại và nguồn:** Xác lập; [G1], [G3], [N1].

**Mục đích:** Đo thời gian hoàn thành ván cho hiển thị và kỷ lục.

**Yêu cầu:** getElapsedSeconds phải trả 0 khi READY. Trong PLAYING, hệ thống phải lấy chênh lệch thời điểm hiện tại với startTimeMillis và quy đổi thành số giây nguyên. Khi WON hoặc LOST, giá trị phải dựa trên endTimeMillis đã chốt và không tiếp tục tăng. Giao diện phải làm mới đồng hồ theo CLOCK_REFRESH_MS hiện tại là 200 ms.

**Điều kiện và ngoại lệ:** Nguồn định hướng sử dụng System.currentTimeMillis; không quy định pause hoặc loại trừ thời gian khi mở menu/hộp thoại. Cắm cờ trước lần mở đầu không được tính thời gian.

**Nghiệm thu:** Đợi khi READY rồi mở ô; thời gian bắt đầu từ 0. Sau thắng hoặc thua, đợi thêm ít nhất hai nhịp làm mới và kiểm tra giá trị không đổi.

#### FR 18 Giới hạn thời gian hiển thị và ghi nhận

**Phân loại và nguồn:** Xác lập; [G1], [G3], [N1].

**Mục đích:** Giữ đồng hồ đúng giới hạn ba chữ số của thiết kế hiện tại.

**Yêu cầu:** Giá trị getElapsedSeconds không được vượt Constants.MAX_TIME_SECONDS=999. Khi thời gian thực vượt giới hạn, giá trị trả về và đồng hồ phải duy trì 999. Đạt giới hạn không được tự đổi trạng thái ván hoặc tự xác định thắng/thua. Việc xử lý thắng tiếp tục dùng giá trị thời gian mà Game cung cấp.

**Điều kiện và ngoại lệ:** Nguồn hiện tại không cung cấp thời gian xếp hạng riêng vượt 999 giây. Nếu muốn phân biệt các ván dài hơn, phải phê duyệt thay đổi yêu cầu dữ liệu.

**Nghiệm thu:** Điều khiển mốc thời gian kiểm thử tương đương 998, 999 và 1000 giây; lần lượt nhận 998, 999 và 999, ván vẫn PLAYING nếu chưa thắng/thua.

#### FR 19 Khóa thao tác sau kết thúc

**Phân loại và nguồn:** Xác lập; [G1], [G3], [N1].

**Mục đích:** Bảo vệ kết quả cuối ván và tránh xử lý kết thúc lặp.

**Yêu cầu:** Khi trạng thái WON hoặc LOST, các thao tác reveal, toggleFlag và chord phải bị bỏ qua, không đổi bàn, số cờ hoặc thời gian kết thúc. Xử lý kết thúc trên giao diện phải chạy đúng một lần cho mỗi ván. Người chơi vẫn được tạo ván mới, chọn độ khó và xem bảng xếp hạng theo chức năng giao diện.

**Điều kiện và ngoại lệ:** Không vô hiệu hóa mọi nút đã mở để thực hiện khóa, vì khi đang PLAYING các ô số vẫn cần nhận click chord. Trạng thái kết thúc phải được kiểm tra trong logic.

**Nghiệm thu:** Sau thắng/thua, click trái/phải/giữa nhiều lần và gọi refresh; dữ liệu không đổi, hộp thoại kết quả/nhập tên không lặp; ván mới hoạt động.

#### FR 20 Xử lý biên và dữ liệu không hợp lệ

**Phân loại và nguồn:** Xác lập cho hợp đồng Board và reveal/toggleFlag; Đề xuất làm rõ cho guard chord ngoài bàn; [G2], [G3], [N1].

**Mục đích:** Giữ hợp đồng kỹ thuật nhất quán và ngăn lỗi thao tác.

**Yêu cầu:** Board phải từ chối số hàng/cột không dương hoặc mineCount ngoài [1, rows×cols−1] bằng IllegalArgumentException. Board.getCell phải ném IndexOutOfBoundsException khi tọa độ sai. Ở lớp Game, reveal và toggleFlag phải bỏ qua tọa độ sai. Đề xuất chuẩn hóa chord cùng hành vi bỏ qua, vì nguồn chưa mô tả guard này rõ ràng.

**Điều kiện và ngoại lệ:** Hợp đồng nội bộ có exception không thay thế yêu cầu Game bảo vệ thao tác. Không bổ sung hành vi với null Random, null Difficulty hoặc tràn số nguyên nếu chưa chốt phạm vi.

**Nghiệm thu:** Kiểm tra Board(0,9,1), Board(2,2,0), Board(2,2,4) bị từ chối; getCell ngoài bàn ném đúng lỗi; các thao tác Game ngoài bàn không thay đổi ván theo hợp đồng đã chốt.

### Giao diện và bảng thành tích

Các yêu cầu FR-21 đến FR-35 là Xác lập theo các nguồn ghi tại từng mục, trừ các mệnh đề được đánh dấu Đề xuất.

#### FR 21 Khởi tạo cửa sổ và bố cục chính

**Yêu cầu.** Hệ thống phải tạo và hiển thị `MainFrame` trên Swing Event Dispatch Thread. Cửa sổ phải có tiêu đề `Minesweeper`, đặt thanh trạng thái phía trên và bàn cờ ở giữa, được căn giữa màn hình và không cho thay đổi kích thước thủ công. Khi khởi động, hệ thống phải khởi tạo bảng xếp hạng từ `leaderboard.txt` trong thư mục làm việc, sau đó tạo ván Beginner ở trạng thái READY. Bàn cờ mới phải có 9 hàng, 9 cột, 10 mìn; bộ đếm hiển thị `010`, thời gian `000`, mặt `:)`. Nút ô có kích thước ưu tiên 30 × 30 px; kích thước riêng vùng ô lần lượt là 270 × 270, 480 × 480 và 900 × 480 px cho ba độ khó. Kích thước cửa sổ còn bao gồm menu, header và viền.

**Ngoại lệ.** Lỗi đọc bảng điểm không được diễn giải thành lỗi khởi tạo luật chơi; cách thông báo được đề xuất tại FR 32.

**Nghiệm thu.** Khởi chạy bằng entry point `yam.Main`; cửa sổ và trạng thái ban đầu đúng các giá trị trên, không phát sinh `UnsupportedOperationException`.

**Nguồn.** [G1], [G4], [G6], [N1].

#### FR 22 Menu ván mới và thay đổi độ khó

**Yêu cầu.** Menu `Game` phải gồm `New` với phím F2; ba lựa chọn `Beginner`, `Intermediate`, `Expert` trong một nhóm radio; `Best Times...`; và `Exit`. Menu `Help` phải gồm `How to Play` và `About`. Lệnh New và nút mặt phải tạo ván mới với độ khó hiện hành. Chọn độ khó phải tạo bàn đúng preset, dừng timer cũ, đặt lại cờ xử lý kết thúc ván, dựng lại lưới, làm mới giao diện và gọi `pack()` để cửa sổ vừa nội dung. Đồng hồ và số cờ của ván trước không được chuyển sang ván mới.

**Ngoại lệ.** Nguồn không yêu cầu hộp xác nhận bỏ ván đang chơi. **Đề xuất:** chọn lại độ khó đang chọn cũng tạo ván mới để hành vi nhất quán.

**Nghiệm thu.** Từ Expert chuyển Beginner chỉ còn 81 nút; F2 và nút mặt tạo READY cùng độ khó, thời gian `000`, không còn cờ, menu radio khớp bàn đang hiển thị.

**Nguồn.** [G1], [G4], [N1].

#### FR 23 Tiếp nhận thao tác chuột trên ô

**Yêu cầu.** Bàn cờ phải phân biệt ba loại chuột qua `MouseListener` hoặc `MouseAdapter`: chuột trái gửi yêu cầu mở ô; chuột phải gửi yêu cầu đảo cờ; chuột giữa gửi yêu cầu chord. Mỗi callback phải mang đúng hàng và cột của ô được tác động. Click trái ô số đã mở phải được luật chơi xử lý như chord. `BoardPanel` chỉ chuyển sự kiện sang `CellActionListener`; luật mở ô, cắm cờ, thắng và thua thuộc `Game`. Sau mỗi hành động, hệ thống phải đọc lại trạng thái model để cập nhật lưới và header.

**Ngoại lệ.** Ô đã mở vẫn phải enabled để nhận chord. Hành động sau WON hoặc LOST phải bị luật chơi bỏ qua, dù nút vẫn nhận sự kiện chuột.

**Nghiệm thu.** Click riêng từng loại chuột trên một tọa độ tạo đúng một loại callback tương ứng; chuột phải không mở ô; chuột giữa không đảo cờ; trái ô số đủ cờ thực hiện chord theo luật.

**Nguồn.** [G1], [G4], [N1].

#### FR 24 Biểu diễn trạng thái ô

**Yêu cầu.** Ô HIDDEN phải có nền xám RGB(192,192,192), viền nổi và không chữ. Ô FLAGGED hiển thị `F`; khi LOST, cờ đặt trên ô không có mìn phải hiển thị `X`. Ô REVEALED có viền phẳng, nền RGB(224,224,224): số 0 để trống, số 1–8 hiển thị số tương ứng, mìn hiển thị `*`; ô mìn gây thua có nền đỏ RGB(255,0,0). Màu số theo thứ tự 1–8 phải là xanh lam, xanh lá, đỏ, navy, đỏ nâu, teal, đen, xám theo `NUMBER_COLORS`. `CellButton` phải vẽ từ `Cell` và `GameState`, không giữ bản sao luật chơi riêng.

**Ngoại lệ.** Cờ đúng sau thua vẫn hiển thị `F`; không đổi tất cả cờ thành `X`. Không được disable ô mở khiến màu số bị xám và chord bị chặn.

**Nghiệm thu.** Dùng bàn kiểm thử chứa đủ ô ẩn, cờ đúng/sai, số 0–8, mìn và mìn nổ; mọi ký hiệu và màu tương ứng đúng model.

**Nguồn.** [G4], [G6], [N1].

#### FR 25 Thanh trạng thái và bộ đếm ba ký tự

**Yêu cầu.** Header phải bố trí bộ đếm mìn bên trái, nút mặt ở giữa và đồng hồ bên phải. Bộ đếm lấy tổng mìn trừ số cờ đang đặt, cho phép âm. Mặt phải là `:)` khi READY hoặc PLAYING, `B)` khi WON và `X(` khi LOST. Hàm định dạng hiển thị phải chặn giá trị trong khoảng −99 đến 999 rồi biểu diễn đúng ba ký tự: giá trị không âm có số 0 đệm, giá trị âm gồm dấu trừ và hai chữ số. Việc chặn này chỉ áp dụng nhãn, không thay đổi dữ liệu số cờ hoặc số mìn trong model.

**Ngoại lệ.** Nếu người chơi đặt quá nhiều cờ và bộ đếm thực thấp hơn −99, nhãn hiển thị `-99`. Cắm cờ không chứng minh vị trí đó có mìn.

**Nghiệm thu.** Đầu vào 0, 7, 99, 999, 1500, −5, −99, −100 cho kết quả `000`, `007`, `099`, `999`, `999`, `-05`, `-99`, `-99`.

**Nguồn.** [G1], [G4], [G6], [N1].

#### FR 26 Cập nhật đồng hồ trên giao diện

**Yêu cầu.** Giao diện phải dùng `javax.swing.Timer` để đọc `Game.getElapsedSeconds()` và cập nhật nhãn mỗi 200 ms. Khi READY, thời gian phải bằng 0; cắm cờ trước lần mở ô hợp lệ đầu tiên không bắt đầu tính giờ. Khi PLAYING, nhãn phải phản ánh số giây nguyên do Game cung cấp. Khi WON hoặc LOST, đồng hồ phải dừng và giữ kết quả cuối ván. Giá trị thời gian của Game tối đa 999 giây; đạt 999 không tự kết thúc ván. Ván mới phải dừng timer cũ và đưa nhãn về `000`.

**Ngoại lệ.** Chu kỳ làm mới 200 ms không phải đơn vị ghi điểm; điểm vẫn tính theo giây nguyên. **Đề xuất:** mở dialog giữa ván không tạm dừng thời gian vì nguồn không định nghĩa trạng thái PAUSED.

**Nghiệm thu.** Chờ sau khi cắm cờ ở READY vẫn thấy `000`; reveal bắt đầu đếm; sau thắng/thua chờ thêm hai giây không đổi kết quả.

**Nguồn.** [G1], [G4], [G6], [N1].

#### FR 27 Xử lý kết thúc ván đúng một lần

**Yêu cầu.** Sau mỗi thao tác, giao diện phải làm mới trạng thái, điều khiển timer và kiểm tra game over. Mỗi ván chỉ được gọi quy trình kết thúc một lần bằng cờ `gameOverHandled`; cờ phải được đặt lại khi tạo ván mới. Khi WON và thời gian đủ điều kiện bảng xếp hạng, hệ thống phải hỏi tên, tạo `ScoreEntry` với độ khó của ván, thời gian cuối ván và `LocalDate.now()`, thêm điểm, lưu file rồi mở Best Times. Khi LOST, giao diện phải thể hiện bàn thua, không hỏi tên và không thêm điểm.

**Ngoại lệ.** Thắng không đủ điều kiện không kích hoạt hỏi tên; nguồn không yêu cầu tự mở Best Times trong trường hợp này. Lỗi lưu không được khiến cùng ván được thêm lại qua các lần click sau.

**Nghiệm thu.** Sau một ván thắng đủ điều kiện, click nhiều lần không mở lại hộp tên và không thêm record thứ hai; bắt đầu ván mới cho phép ghi nhận kết quả mới.

**Nguồn.** [G4], [G5], [N1].

#### FR 28 Thu nhận và chuẩn hóa tên người chơi

**Yêu cầu.** Hộp nhập tên phải sử dụng giá trị mặc định `Anonymous` khi người chơi Cancel, trả null hoặc nhập chỉ khoảng trắng. `ScoreEntry` phải chuẩn hóa tên trước lưu: trim, loại dấu `;`, giới hạn tối đa 20 ký tự, dùng `Anonymous` nếu kết quả null hoặc rỗng. Cancel hộp tên vẫn ghi điểm với tên mặc định theo contract hiện có; không được hiểu Cancel là hủy thành tích. Tên phải được xử lý ở tầng dữ liệu, kể cả khi bản ghi không đến từ hộp thoại.

**Ngoại lệ và đề xuất.** Nguồn chưa quy định CR/LF hoặc cách đếm Unicode. **Đề xuất:** loại CR/LF và ký tự điều khiển; trim lại sau khi loại separator, dùng Anonymous nếu kết quả chỉ khoảng trắng; giới hạn 20 Unicode code point để không cắt giữa cặp surrogate; dùng UTF-8 khi lưu. Các điểm này cần được thống nhất khi triển khai.

**Nghiệm thu.** Null, blank và tên chỉ gồm `;` thành `Anonymous`; tên có `;` không phá cấu trúc bốn trường; tên vượt giới hạn được cắt theo quy ước đã chốt.

**Nguồn.** [G4], [G5], [G6], [N1].

#### FR 29 Xác định thứ hạng và điều kiện nhận điểm

**Yêu cầu.** Thành tích phải được sắp theo thời gian tăng dần; thời gian bằng nhau thì ngày sớm hơn đứng trước. Nếu thời gian và ngày bằng nhau, bản ghi được chèn trước phải đứng trước. Một kết quả đủ điều kiện nếu độ khó tương ứng có dưới 10 bản ghi hoặc thời gian mới nhỏ hơn nghiêm ngặt thời gian tệ nhất của độ khó đó. Khi đã đủ 10, thời gian bằng mức tệ nhất không đủ điều kiện. Việc so sánh không trộn điều kiện nhận điểm giữa các độ khó.

**Ngoại lệ.** Ngày lưu chỉ có độ phân giải ngày, không có timestamp. Thứ tự trong cùng ngày dựa vào tính ổn định của `addSorted`. Vì thời gian Game bị chặn ở 999, ván dài hơn 999 vẫn dùng 999 theo contract hiện tại.

**Nghiệm thu.** Với 10 điểm và worst = 100: 99 đủ điều kiện, 100 và 101 không đủ; với 9 điểm, mọi thời gian hợp lệ đủ điều kiện. Điểm cùng thời gian được sắp đúng ngày rồi thứ tự chèn.

**Nguồn.** [G1], [G5], [G6], [N1].

#### FR 30 Quản lý danh sách bảng xếp hạng

**Yêu cầu.** Tất cả thành tích phải nằm trong một `MyLinkedList<ScoreEntry>` duy nhất, duy trì thứ tự bằng `addSorted` với comparator tự nhiên; không sắp xếp lại toàn bộ danh sách sau mỗi lần thêm. `addScore` phải chèn rồi loại các điểm tệ nhất vượt quá 10 của đúng độ khó được thêm. Sau thao tác hoàn tất, mỗi độ khó tối đa 10 bản ghi, toàn bộ tối đa 30. `getTopScores` phải trả danh sách mới, lọc đúng độ khó và giữ thứ tự. `clear` chỉ xóa dữ liệu trong bộ nhớ; file chỉ đổi sau save.

**Ngoại lệ.** Không có yêu cầu nút xóa bảng điểm trong UI. Danh sách trả về phải độc lập về cấu trúc; nguồn không yêu cầu sao chép sâu từng ScoreEntry.

**Nghiệm thu.** Thêm 15 điểm Beginner giữ 10 tốt nhất; điểm Intermediate/Expert không mất. Clear/remove trên kết quả getTopScores không đổi danh sách nội bộ. Save-load vẫn giữ thứ tự các điểm bằng nhau.

**Nguồn.** [G1], [G5], [G6], [N1].

#### FR 31 Định dạng và phân tích bản ghi điểm

**Yêu cầu.** File `leaderboard.txt` phải đặt trong working directory. Mỗi dòng chứa đúng bốn trường phân cách bằng `;`: `name;DIFFICULTY;timeSeconds;yyyy-MM-dd`. Độ khó phải là enum name `BEGINNER`, `INTERMEDIATE` hoặc `EXPERT`; thời gian là số nguyên; ngày theo `LocalDate`. Ví dụ hợp lệ: `Alice;BEGINNER;42;2026-10-22`. Phương thức parse phải báo `IllegalArgumentException` nếu dòng sai cấu trúc hoặc không phân tích được trường; lỗi parsing ngày cũng phải được chuyển về kiểu lỗi này để tầng load xử lý thống nhất.

**Ngoại lệ và đề xuất.** Parse được số âm hoặc số trên 999 chưa chứng minh bản ghi hợp lệ. **Đề xuất:** chỉ nhận thời gian 0–999; dòng ngoài khoảng bị coi là hỏng. Encoding UTF-8 là yêu cầu bổ sung, chưa được nguồn chỉ định.

**Nghiệm thu.** ToFileLine rồi FromFileLine bảo toàn bốn trường; dòng thiếu/thừa trường, enum không tồn tại, thời gian không phải số hoặc ngày sai gây lỗi parse có kiểm soát.

**Nguồn.** [G1], [G5], [G6], [N1].

#### FR 32 Nạp bảng xếp hạng và chịu lỗi dữ liệu

**Yêu cầu.** Load phải thay thế dữ liệu đang có bằng nội dung file, bắt đầu bằng clear. Nếu file không tồn tại, load phải trả true và để bảng trống. Nếu file có, hệ thống phải đọc từng dòng, parse rồi addScore; dòng gây `IllegalArgumentException` phải bị bỏ qua và không ngăn đọc các dòng sau. Nếu file tồn tại nhưng không đọc được do IOException, load phải trả false. Danh sách sau nạp thành công phải đáp ứng giới hạn 10 điểm mỗi độ khó.

**Ngoại lệ và đề xuất.** Nguồn chưa định nghĩa dữ liệu còn lại khi lỗi xảy ra giữa quá trình đọc. **Đề xuất:** bảng ở trạng thái lỗi phải được làm rỗng và hiện thông báo không đọc được; người dùng vẫn chơi được. Không được cam kết giữ dữ liệu cũ vì contract hiện yêu cầu clear trước đọc.

**Nghiệm thu.** File không tồn tại cho bảng trống; file gồm dòng tốt, dòng hỏng, dòng tốt vẫn giữ hai dòng tốt; lỗi đọc trả false, không làm crash game theo chính sách đề xuất.

**Nguồn.** [G5], [N1].

#### FR 33 Lưu bảng xếp hạng và xử lý lỗi ghi

**Yêu cầu.** Save phải ghi mọi bản ghi đang giữ, một `toFileLine()` trên mỗi dòng, và đóng tài nguyên đọc ghi đúng cách. Save phải trả false nếu phát sinh IOException; không được coi false là lưu thành công. Việc ghi phải xảy ra sau khi thêm thành tích thắng đủ điều kiện. Các trường phải giữ nguyên sau chu trình save, tạo đối tượng Leaderboard mới và load.

**Ngoại lệ và đề xuất.** Nguồn chỉ quy định boolean kết quả, chưa quy định thông báo UI hoặc ghi nguyên tử. **Đề xuất:** khi ghi lỗi, giữ điểm trong RAM cho phiên hiện tại, thông báo chưa lưu xuống đĩa, vẫn mở bảng điểm và cho tiếp tục chơi. Có thể ghi file tạm rồi thay thế để giảm nguy cơ file dở dang; đây là đề xuất độ tin cậy cần chốt, không phải tính năng đã xác nhận.

**Nghiệm thu.** Save-load giữ dữ liệu và thứ tự; đường dẫn không ghi được trả false; không hỏi tên lại hay nhân đôi điểm do lỗi ghi.

**Nguồn.** [G4], [G5], [N1].

#### FR 34 Tra cứu thành tích theo độ khó

**Yêu cầu.** Best Times phải là dialog modal có ba tab theo thứ tự `Difficulty.values()`, tiêu đề tab lấy từ displayName. Mỗi tab phải có bảng không cho chỉnh sửa với các cột `#`, `Name`, `Time (s)`, `Date`, hiển thị kết quả `getTopScores` đúng độ khó. Thứ hạng phải đánh từ 1 theo vị trí trong tab; thời gian hiển thị giây; ngày khớp record. Bảng phải nằm trong `JScrollPane`; không có điểm thì hiển thị bảng rỗng. Nút `Close` phải đóng dialog bằng dispose, không thoát ứng dụng. Dialog phải pack và căn giữa theo cửa sổ chính.

**Ngoại lệ.** Không có yêu cầu sửa, xóa, tìm kiếm hoặc gửi điểm trực tuyến trong dialog. Modal ngăn thao tác cửa sổ chủ trong lúc dialog mở; chính sách thời gian giữa ván theo FR 26.

**Nghiệm thu.** Ba tab hiển thị đúng dữ liệu riêng, tối đa 10 hàng mỗi tab; không sửa được ô; mở bảng trống và đóng bằng Close không gây lỗi.

**Nguồn.** [G4], [G5], [N1].

#### FR 35 Trợ giúp thông tin và thoát ứng dụng

**Yêu cầu.** `Help → How to Play` phải mở hộp thông tin nêu mục tiêu mở mọi ô không mìn; nghĩa của số 1–8; trái mở ô; phải đặt hoặc gỡ cờ; giữa hoặc trái trên ô số để chord khi đủ cờ; click đầu an toàn; mở mìn kết thúc ván. `Help → About` phải giới thiệu game Minesweeper clone và dự án nhóm môn CSD201 Data Structures and Algorithms. `Game → Exit` và nút đóng cửa sổ chính phải kết thúc ứng dụng. Các hộp thông tin phải đóng được để quay lại game.

**Ngoại lệ.** Nguồn chưa yêu cầu lưu ván đang chơi, hỏi xác nhận thoát hay lưu độ khó lần trước; không coi các khả năng này là bắt buộc. Nội dung UI nguồn là tiếng Anh; SRS tiếng Việt không tự đặt thêm yêu cầu Việt hóa ứng dụng.

**Nghiệm thu.** Mở từng mục Help và kiểm tra nội dung đúng; đóng hộp trở về bàn hiện hành; Exit và nút đóng kết thúc tiến trình GUI; lần mở sau khởi tạo Beginner và nạp bảng điểm đã lưu.

**Nguồn.** [G1], [G4], [N1].

## 5 Kịch bản sử dụng chi tiết

### Danh mục kịch bản

| Mã | Kịch bản | Tác nhân | Điều kiện thành công |
| --- | --- | --- | --- |
| UC-01 | Khởi động ứng dụng | Người chơi và hệ thống tệp | Mở cửa sổ với bàn Beginner READY và dữ liệu thành tích đã nạp |
| UC-02 | Tạo ván hoặc đổi độ khó | Người chơi | Một Game mới đúng cấu hình thay thế toàn bộ ván cũ |
| UC-03 | Mở ô lần đầu | Người chơi | Sinh đúng mìn, bảo vệ vùng đầu, bắt đầu thời gian và mở ô |
| UC-04 | Mở ô và mở vùng | Người chơi | Mở đúng tập ô an toàn hoặc chuyển sang LOST khi gặp mìn |
| UC-05 | Đặt và bỏ cờ | Người chơi | Đổi cờ và bộ đếm mà không mở ô |
| UC-06 | Mở nhanh bằng chord | Người chơi | Chỉ mở hàng xóm hợp lệ khi số cờ bằng số trên ô |
| UC-07 | Kết thúc thắng và ghi kỷ lục | Người chơi và hệ thống tệp | WON ổn định, xét top và lưu thành tích theo điều kiện |
| UC-08 | Kết thúc thua | Người chơi | LOST ổn định, phân biệt mìn nổ và cờ sai |
| UC-09 | Xem thành tích | Người chơi | Xem danh sách riêng từng độ khó mà không sửa dữ liệu |
| UC-10 | Xem trợ giúp và thoát | Người chơi | Đọc đúng hướng dẫn hoặc kết thúc tiến trình |

### UC 01 Khởi động ứng dụng

Tiền điều kiện: môi trường Java phù hợp; không yêu cầu có sẵn tệp thành tích. Kích hoạt: chạy yam.Main qua NetBeans hoặc JAR.

Luồng chính: Main yêu cầu tạo giao diện trên Event Dispatch Thread; MainFrame khởi tạo Leaderboard, gọi load, tạo menu, header và bàn; tạo Game Beginner; cập nhật đồng hồ 000, bộ đếm 010 và mặt bình thường; căn cửa sổ giữa màn hình; hiển thị giao diện. Sau luồng này không có mìn được đặt và thời gian chưa chạy.

Luồng thay thế: không có leaderboard.txt thì sử dụng bảng rỗng; tệp chứa dòng hỏng thì bỏ qua riêng dòng đó; lỗi đọc tệp được trả về từ tầng score. Việc thông báo lỗi đọc cho người chơi là đề xuất FR thuộc lưu đọc. Hậu điều kiện: người chơi có thể bắt đầu ván mới dù chưa có thành tích.

### UC 02 Tạo ván và đổi độ khó

Tiền điều kiện: cửa sổ chính đang mở ở bất kỳ trạng thái ván nào. Kích hoạt: Game → New, phím F2, nút mặt hoặc mục Beginner, Intermediate, Expert.

Luồng chính: chọn độ khó hiện tại nếu là New hoặc nút mặt; dừng bộ cập nhật giờ của ván cũ; tạo Game mới; đặt lại gameOverHandled; xây lại lưới đúng số hàng cột; refresh tất cả thành phần; pack cửa sổ. Cờ và ô mở của ván cũ không được mang sang. Thành tích đã lưu không bị xóa.

Nguồn hiện tại không yêu cầu hộp thoại xác nhận bỏ ván; đường cơ sở cho phép thay ván ngay. Nếu nhóm muốn bổ sung xác nhận thì phải coi là thay đổi yêu cầu, không chặn New bằng một quy trình tự phát. Hậu điều kiện: READY, tất cả ô ẩn, flagsPlaced bằng 0, giờ bằng 0 và bộ đếm bằng số mìn cấu hình.

### UC 03 Mở ô lần đầu

Tiền điều kiện: READY, ô được chọn ở trong bàn và chưa cắm cờ. Kích hoạt: nhấn trái một ô ẩn.

Luồng chính: xác định vùng bảo vệ; liệt kê vị trí đủ điều kiện đặt mìn; nếu thiếu chỗ thì thu vùng bảo vệ về riêng ô chọn; chọn mìn bằng Fisher–Yates; tính số lân cận trên ô an toàn; ghi giờ bắt đầu và PLAYING; mở ô qua thuật toán đang cấu hình; kiểm tra thắng; làm mới giao diện và bộ cập nhật giờ.

Luồng thay thế: click lên ô mang cờ hoặc tọa độ ngoài bàn bị bỏ qua; vẫn READY, không đặt mìn và không bắt đầu giờ. Cờ đặt trước lần mở vẫn tồn tại, nhưng không bảo vệ vị trí đó khỏi việc được chọn làm mìn. Thứ tự và seed quyết định bản đồ kiểm thử; không đòi mọi vị trí click sinh cùng một bản đồ.

### UC 04 Mở ô trong ván

Tiền điều kiện: PLAYING. Luồng chính khi ô ẩn an toàn có số từ 1 đến 8: chuyển ô đó sang REVEALED và tăng bộ đếm một lần. Khi ô có số 0: mở vùng các ô HIDDEN số 0 có thể đến được theo tám hướng mà không đi qua ô FLAGGED hoặc ô đã REVEALED trước thao tác, cùng các ô số HIDDEN an toàn tiếp giáp vùng này. Sau đó kiểm tra thắng và refresh.

Luồng thay thế: ô mang cờ không mở; ô đã mở chuyển sang xử lý chord; mìn chuyển sang UC-08. Sự có mặt của cờ sai có thể ngăn một ô an toàn mở trong flood fill; người chơi phải bỏ cờ hoặc mở bằng thao tác hợp lệ sau đó. Hậu điều kiện: không có ô nào tăng revealedCount hai lần và không có mìn nào được flood fill làm lộ trong một ván còn PLAYING.

### UC 05 Cắm và bỏ cờ

Tiền điều kiện: READY hoặc PLAYING, tọa độ trong bàn. Kích hoạt: nhấn phải. HIDDEN chuyển FLAGGED và tăng flagsPlaced; FLAGGED chuyển HIDDEN và giảm flagsPlaced. Header tính lại tổng mìn trừ flagsPlaced. REVEALED không đổi. Không giới hạn số cờ ở số mìn; kết quả bộ đếm có thể âm.

Cắm cờ không khởi động ván, không sinh mìn và không làm thắng kể cả khi số cờ bằng số mìn. Khi WON hoặc LOST, toàn bộ thao tác này bị bỏ qua. Đây là quy tắc bảo toàn ván, không phụ thuộc nút GUI có bị khóa hay không.

### UC 06 Chord

Tiền điều kiện: PLAYING; ô trung tâm đã mở, số trên ô lớn hơn 0 và số cờ lân cận bằng số đó. Kích hoạt: nhấn giữa hoặc nhấn trái ô số đã mở.

Luồng chính: duyệt hàng xóm theo thứ tự trả về của Board; bỏ qua ô mang cờ hoặc đã mở; mở từng ô ẩn bằng cùng quy trình mở ô thông thường; nếu chưa thua thì kiểm tra thắng sau vòng duyệt. Khi số cờ khác số hiển thị trên ô trung tâm, thao tác không đổi bàn. Chord lên ô 0 hoặc ô chưa mở không tạo hiệu ứng.

Luồng lỗi có chủ đích: số lượng cờ đúng nhưng một hoặc nhiều cờ đặt sai thì có thể mở trúng mìn. Hệ thống chuyển LOST ngay và dừng mở các hàng xóm tiếp theo. Không có cam kết chord luôn an toàn; điều kiện chỉ so số lượng cờ, không kiểm chứng tính đúng của từng cờ cho người chơi.

### UC 07 Thắng và ghi kỷ lục

Tiền điều kiện: sau một thao tác, state vẫn PLAYING và số ô an toàn đã mở đủ. Hệ thống chuyển WON; ghi mốc kết thúc; tự cắm cờ mìn; flagsPlaced bằng tổng mìn; dừng cập nhật giờ; vẽ mặt B). MainFrame đánh dấu đã xử lý kết thúc trước khi mở hộp thoại để không lặp lại chuỗi ghi điểm.

Nếu qualifies trả false, ván giữ WON và không hỏi tên. Nếu true, hỏi tên; hủy hoặc để trống dùng Anonymous; tạo ScoreEntry với độ khó, giây đã chốt và LocalDate.now; chèn có thứ tự, giữ top 10; save; mở bảng thành tích. Việc hủy nhập tên không hủy ván thắng và theo G4 vẫn ghi nhãn mặc định.

Nếu save thất bại, tầng score trả false. Đề xuất xử lý giao diện: giữ thành tích trong bộ nhớ phiên hiện tại và thông báo chưa lưu thành công; không báo thành công bền vững và không chuyển trạng thái ván. Đây là trường hợp cần chốt theo OI-03.

### UC 08 Thua

Kích hoạt: mở trúng mìn bằng reveal hoặc chord. Hệ thống chốt LOST và thời gian; đặt exploded trên đúng ô kích hoạt; mở các mìn chưa có cờ; refresh mặt X(, mìn và cờ sai. Không tự mở những ô an toàn còn ẩn, không thêm thành tích và không hỏi tên.

Sau thua, thử nhấn trái, phải, giữa lên các loại ô không làm đổi dữ liệu. Chỉ New, nút mặt, F2 hoặc chọn độ khó tạo ván khác. Lộ mìn sau thua phục vụ hiển thị, không được làm phát sinh kiểm tra thắng mới.

### UC 09 Xem bảng thành tích

Kích hoạt: Game → Best Times hoặc tự mở sau khi ghi điểm đủ điều kiện. Hệ thống tạo hộp thoại gắn với cửa sổ chính; có ba tab độ khó; mỗi tab hiển thị hạng, tên, thời gian và ngày. Danh sách trống vẫn hiện tiêu đề cột. Bảng không cho sửa trực tiếp ScoreEntry. Đóng hộp thoại quay lại cửa sổ chính mà không tạo Game mới.

Việc giờ có tiếp tục chạy khi mở hộp thoại giữa ván được nêu ở OI-05. Đường cơ sở không có chức năng tạm dừng; đề xuất vẫn tính thời gian thực kể cả lúc hộp thoại đang hiển thị.

### UC 10 Trợ giúp và thoát

How to Play phải giải thích mục tiêu mở mọi ô an toàn, ba loại click, ý nghĩa chữ số và quy tắc click đầu an toàn. About nêu trò chơi Minesweeper và đồ án CSD201. Các hộp thoại này không làm đổi dữ liệu bàn. Game → Exit và nút đóng cửa sổ kết thúc ứng dụng theo EXIT_ON_CLOSE; ván dở không được khôi phục ở lần mở tiếp theo. Không cần lưu ván dở trước khi thoát trong phạm vi phiên bản.

## 6 Giao diện bên ngoài

### Cấu trúc cửa sổ

Cửa sổ dùng tiêu đề Minesweeper, không cho người dùng resize theo hợp đồng G4. Menu đặt phía trên, header ở NORTH và lưới ở CENTER. Header gồm bộ đếm mìn bên trái, nút mặt ở giữa và thời gian bên phải. Mỗi ô có kích thước ưu tiên 30 × 30 px; lưới tương ứng là 270 × 270, 480 × 480 và 900 × 480 px trước khi cộng phần trang trí cửa sổ.

| Thành phần | Nội dung và thao tác | Điều kiện |
| --- | --- | --- |
| Game → New | Bắt đầu lại độ khó hiện tại, có F2 | Luôn dùng được trong cửa sổ chính |
| Game → Beginner | Radio item 9 × 9 và 10 mìn | Chọn tạo ván mới và cập nhật lựa chọn |
| Game → Intermediate | Radio item 16 × 16 và 40 mìn | Chung một ButtonGroup với hai độ khó còn lại |
| Game → Expert | Radio item 16 × 30 và 99 mìn | pack lại cửa sổ sau xây lưới |
| Game → Best Times | Mở bảng thành tích | Không sửa bàn hiện tại |
| Game → Exit | Kết thúc ứng dụng | Không lưu ván dở |
| Help → How to Play | Hiện hướng dẫn | Nội dung đồng nhất với luật chơi |
| Help → About | Hiện giới thiệu | Không tác động game |
| Nút mặt | Tạo ván mới cùng độ khó | READY và PLAYING dùng :), WON dùng B), LOST dùng X( |

Các nhãn tiếng Anh hiện có trong hợp đồng nguồn được giữ làm đường cơ sở. SRS viết tiếng Việt không tự tạo yêu cầu bản địa hóa toàn bộ UI. Nếu đổi sang tiếng Việt, cần đổi đồng bộ thông điệp hướng dẫn và tài liệu thao tác.

### Trình bày ô

| Tình huống | Chữ | Nền và viền |
| --- | --- | --- |
| HIDDEN | Rỗng | Xám 192 192 192, viền nổi |
| FLAGGED khi chưa thua hoặc cờ đúng | F | Kiểu ô có cờ |
| FLAGGED trên ô an toàn sau LOST | X | Phân biệt cờ sai |
| REVEALED an toàn có số 0 | Rỗng | Xám 224 224 224, viền phẳng |
| REVEALED an toàn có số 1 đến 8 | Số tương ứng | Nền ô mở và màu chữ theo bảng |
| REVEALED có mìn | * | Nền ô mở |
| Mìn gây nổ | * | Nền đỏ 255 0 0 |

Màu số theo G4: 1 xanh dương RGB 0 0 255; 2 xanh lá 0 128 0; 3 đỏ 255 0 0; 4 xanh navy 0 0 128; 5 đỏ maroon 128 0 0; 6 xanh teal 0 128 128; 7 đen 0 0 0; 8 xám 128 128 128. Luôn có chữ số hoặc ký hiệu bên cạnh màu để nhận biết nội dung. Ô đã mở vẫn enabled để nhận chord và giữ màu chữ.

### Hợp đồng đầu vào và đầu ra nội bộ

| Giao tiếp | Đầu vào | Đầu ra hoặc hiệu ứng | Quy tắc |
| --- | --- | --- | --- |
| CellActionListener | onReveal, onToggleFlag, onChord với row col | Chuyển yêu cầu đến MainFrame | Một click hợp lệ phải tạo một hành động tương ứng |
| Game | Tọa độ thao tác | Bàn, trạng thái, số cờ, thời gian | UI đọc lại model sau thao tác |
| Board | Kích thước, số mìn, tọa độ | Cell và MyLinkedList các hàng xóm | Không phụ thuộc Swing, không quyết định thời gian |
| MinePlacer | Board, tọa độ an toàn, Random | Đặt cờ mine trong Cell | Không xử lý menu hoặc thành tích |
| FloodFill | Board và tọa độ gốc | Số ô vừa mở và biến đổi bàn | Không gọi đệ quy, không mở mìn hoặc cờ |
| Leaderboard | Độ khó, ScoreEntry, đường dẫn tệp | Kết quả qualifies, bản sao danh sách, boolean I/O | Không lộ trực tiếp danh sách nội bộ |
| CellButton render | Cell và GameState | Chữ, màu và viền | Không sở hữu trạng thái luật chơi độc lập |

Không có yêu cầu HTTP, socket, dịch vụ bên ngoài hoặc giao tiếp thiết bị đặc thù. Ngoại lệ ở tầng DS và Board phục vụ người gọi API; click người chơi ngoài phạm vi hợp lệ phải được Game chặn trước khi gọi getCell.

## 7 Mô hình dữ liệu và lưu trữ

### Từ điển dữ liệu

| Thực thể | Thuộc tính | Miền giá trị và ý nghĩa | Vòng đời |
| --- | --- | --- | --- |
| Difficulty | displayName, rows, cols, mines | Ba cấu hình preset ở mục 3 | Cố định theo enum |
| Game | board, difficulty, state | Một bàn và một độ khó cho một ván | Tạo lại khi New |
| Game | flagsPlaced | Số ô đang mang cờ trong ván; sau thắng bằng mineCount | Cập nhật khi đảo cờ hoặc thắng |
| Game | startTimeMillis, endTimeMillis | Mốc đo thời gian trong bộ nhớ; chưa cần lưu tệp | Từ reveal đầu đến kết thúc |
| Game | random | Bộ sinh ngẫu nhiên, có thể truyền seed để kiểm thử | Một Game |
| Board | rows, cols, mineCount | rows và cols dương; 1 ≤ mineCount ≤ rows × cols − 1 | Một bàn |
| Board | cells | Cell[rows][cols], mỗi tọa độ có một Cell | Một bàn |
| Board | revealedCount | Số ô an toàn được mở qua luồng chơi hợp lệ | Chỉ tăng khi revealCell đổi trạng thái |
| Cell | row, col | Tọa độ gắn với vị trí trong bàn | Cố định về ý nghĩa |
| Cell | mine | true nếu được MinePlacer chọn | Chốt sau đặt mìn |
| Cell | adjacentMines | 0 đến 8 trên ô an toàn | Tính sau đặt mìn |
| Cell | state | HIDDEN, FLAGGED, REVEALED | Theo thao tác và kết quả ván |
| Cell | exploded | true cho ô mìn kích hoạt thua | Chỉ phục vụ kết quả thua |
| ScoreEntry | playerName | Tên đã làm sạch, tối đa 20 ký tự theo quy ước được chốt | Qua bộ nhớ và tệp |
| ScoreEntry | difficulty | BEGINNER, INTERMEDIATE hoặc EXPERT | Một thành tích |
| ScoreEntry | timeSeconds | Số giây nguyên từ Game, đường chơi hiện tại 0 đến 999 | Một thành tích |
| ScoreEntry | date | LocalDate; lưu yyyy-MM-dd | Một thành tích |
| Leaderboard | entries | Một MyLinkedList của mọi độ khó, luôn theo thứ tự so sánh | Phiên chạy và tệp |
| Leaderboard | filePath | Mặc định leaderboard.txt trong working directory | Cấu hình khi tạo Leaderboard |
| MainFrame | gameOverHandled | false cho Game mới, true trước xử lý kết thúc | Tránh thêm cùng kết quả hai lần |

Một Game có đúng một Board; Board chứa rows × cols Cell. Leaderboard chứa không quá 10 ScoreEntry của mỗi Difficulty sau trim. Không lưu Board, tọa độ mìn, cờ hoặc GameState xuống leaderboard.txt. Tên người chơi không phải khóa duy nhất: hai người hoặc hai ván có thể dùng cùng tên và vẫn là các bản ghi khác nhau.

### Quy tắc đồng nhất dữ liệu

Khi READY, mọi mine là false và revealedCount bằng 0; có thể có Cell FLAGGED nếu người chơi đã cắm cờ. Sau đặt mìn, tổng ô mine phải bằng mineCount. flagsPlaced phải khớp số ô FLAGGED trong ván còn hoạt động. Sau thắng, mọi ô an toàn mở và mọi ô mìn có cờ. Sau thua, revealedCount giữ số ô an toàn đã mở trước khi lộ mìn, không cộng các mìn vừa hiển thị.

Các setter của Cell phục vụ xây dựng mô hình và fixture; không cho phép UI dùng setter để bỏ qua Game. Bộ đếm được quản lý ở Board là giá trị dẫn xuất có lưu đệm, vì vậy việc gọi trực tiếp Cell.reveal trên ô an toàn trong luồng chơi có thể làm lệch điều kiện thắng. Chỉ các thao tác hiển thị mìn cuối ván mới dùng đường không tăng revealedCount theo hợp đồng nguồn.

### Cấu trúc tệp thành tích

Đường dẫn mặc định là working directory cộng leaderboard.txt, không phải một đường dẫn cố định trong thư mục người dùng hay cạnh JAR. Mỗi dòng là một bản ghi; không có header cột, mã ID hay trường phiên bản trong định dạng nguồn. Thứ tự trường là tên, enum độ khó, số giây, ngày.

```text
An;BEGINNER;42;2026-10-26
Binh;INTERMEDIATE;105;2026-10-25
Chi;EXPERT;280;2026-10-26
```

Ví dụ trên minh họa cú pháp, không phải dữ liệu thực tế của nhóm. Khi save, thứ tự dòng phải theo danh sách hiện có; ví dụ minh họa đã theo thời gian tăng. Khi load, từng dòng được parse rồi addScore nên tệp chưa sắp vẫn có thể tạo danh sách đã sắp và các mục dư được trim.

| Thành phần | Hợp lệ theo nguồn | Sai cấu trúc hoặc cần chốt |
| --- | --- | --- |
| Số trường | Đúng 4 trường | Thiếu hoặc thừa trường phải bị coi là dòng hỏng |
| Tên | Được sanitize, không chứa dấu ; | CR/LF và Unicode xem OI-02 |
| Độ khó | Tên enum viết đúng chữ hoa | Nhãn Beginner hoặc giá trị CUSTOM không phải enum hợp lệ |
| Thời gian | Phân tích được thành int | Số ngoài 0 đến 999 cần chính sách bổ sung ở OI-01 |
| Ngày | Phân tích được thành LocalDate | Ngày không tồn tại hoặc sai cú pháp gây IllegalArgumentException |
| Tệp không có | load true, bảng rỗng | Không phải lỗi khởi động |
| Dòng hỏng | Bỏ riêng dòng, tiếp tục đọc | Không được chấm dứt toàn bộ load vì lỗi parse một dòng |
| IOException | load hoặc save trả false | Phản hồi GUI và dữ liệu đọc dở xem OI-03 |

Đề xuất thống nhất UTF-8 cho tệp điểm để tên tiếng Việt không phụ thuộc encoding mặc định của máy. Source UTF-8 trong cấu hình build chỉ xác định encoding mã nguồn, không tự chứng minh encoding của dữ liệu I/O. Không coi tệp local là nguồn đáng tin cậy tuyệt đối; parser phải chịu được chỉnh sửa thủ công và dữ liệu hỏng.

### Sắp xếp và giới hạn

Quan hệ so sánh tự nhiên của ScoreEntry là cặp timeSeconds rồi date; difficulty không tham gia comparator tự nhiên. Danh sách tổng có thể xen kẽ ba độ khó. Lọc bằng getTopScores tạo danh sách mới và bảo toàn thứ tự tương đối của độ khó được chọn. Mỗi tab đánh hạng từ 1 nên không dùng vị trí trong danh sách tổng làm thứ hạng của tab.

Ví dụ hai điểm Beginner A và B cùng 42 giây, A ngày 25 và B ngày 26 thì A đứng trước. Nếu cả ngày và giây giống nhau, điểm được thêm trước đứng trước nhờ addSorted ổn định. Khi đủ 10 mục, qualifies chỉ nhìn thời gian mới nhỏ hơn nghiêm ngặt thời gian cuối của cùng độ khó; không dùng tên hoặc ngày mới để vượt một kết quả có thời gian bằng cuối bảng. Điều kiện nhận điểm và comparator là hai hợp đồng khác nhau.

### Lưu và phục hồi

load thay dữ liệu bộ nhớ, không cộng dồn qua nhiều lần load. clear không đụng tệp đến khi save. Không có chức năng sửa điểm trong UI. Nguồn không yêu cầu loại bản ghi trùng; không tự gộp những dòng giống nhau khi nạp. Lần save thành công tiếp theo có thể loại các dòng hỏng do chỉ ghi lại những entry đang giữ.

Đề xuất bảo vệ ghi bằng tệp tạm cùng thư mục rồi thay thế tệp đích sau khi đóng stream. Đây là cải tiến độ tin cậy, chưa là nghĩa vụ từ G5. Nếu chưa triển khai, không được tuyên bố lưu nguyên tử hoặc khôi phục tự động sau mất điện. Việc hai tiến trình game cùng ghi một file chưa được hỗ trợ trong đường cơ sở.

## 8 Xử lý lỗi và tình huống biên

| Tình huống | Hành vi theo đường cơ sở | Chính sách bổ sung |
| --- | --- | --- |
| Board có kích thước hoặc số mìn sai | Ném IllegalArgumentException tại constructor | Không yêu cầu dialog nhập bàn tùy ý |
| Board.getCell ngoài phạm vi | Ném IndexOutOfBoundsException | Người gọi phải kiểm tra biên |
| Game.reveal hoặc toggleFlag ngoài phạm vi | Bỏ qua, không làm đổi ván | Chord cùng quy tắc là đề xuất FR-20 |
| Pop hoặc dequeue cấu trúc rỗng | Ném NoSuchElementException | Không thay thế bằng null |
| Iterator hết phần tử | hasNext false, next ném NoSuchElementException | Không yêu cầu fail-fast khi sửa list |
| Cờ nhiều hơn mìn | Hợp lệ, bộ đếm có thể âm | Header chặn nhãn tại −99, model giữ số thực |
| Đồng hồ vượt 999 | Giá trị trả về giữ 999, ván tiếp tục | Không có thời gian xếp hạng riêng dài hơn |
| Chord có cờ sai | Có thể nổ mìn và thua | Dừng mở tiếp sau nổ; không hoàn tác ô đã mở trước đó |
| Hủy nhập tên | Dùng Anonymous và vẫn ghi nếu đủ điều kiện | Không coi Cancel là bỏ thành tích |
| Tệp điểm không có | Bảng rỗng, load thành công | Tạo tệp ở lần save thành công |
| Dòng điểm hỏng | Bỏ dòng và đọc tiếp | Có thể ghi chẩn đoán nhưng không bắt người dùng sửa tệp |
| Tệp tồn tại nhưng lỗi I/O | API trả false | Đề xuất báo rõ, cho tiếp tục chơi, không báo đã lưu |
| Load hỏng giữa chừng | Nguồn chưa chốt danh sách đọc dở | Đề xuất xóa phần đọc dở, xem OI-03 |
| Lỗi ghi điểm | Trạng thái WON giữ nguyên | Đề xuất giữ entry trong RAM và không tự thêm lại |
| Đổi độ khó giữa ván | Bỏ ván cũ và tạo Game mới | Không ghi thành tích cho ván bỏ dở |
| Thiết bị không có nút giữa | Dùng trái lên ô số đã mở để chord | Không đòi thêm tổ hợp phím ngoài nguồn |

Không sử dụng try/catch bao toàn bộ ứng dụng rồi bỏ qua mọi lỗi lập trình. Exception hợp đồng phải có kiểm thử riêng; lỗi dữ liệu dự kiến cần xử lý ở ranh giới load/parse. Thông điệp I/O đề xuất nên nêu tác vụ thất bại, cho biết điểm hiện chỉ có trong phiên và không đưa stack trace vào thông điệp cho người chơi.

## 9 Ràng buộc thiết kế và công nghệ

Các yêu cầu DC dưới đây là ràng buộc của đồ án. Chúng không khẳng định rằng mọi chức năng liên quan đã được triển khai. N là tổng số ô bàn cờ; n là số phần tử trong cấu trúc dữ liệu.

| Mã | Yêu cầu bắt buộc | Tiêu chí chấp nhận | Nguồn |
| --- | --- | --- | --- |
| DC-01 | Mã game phải dùng cấu trúc tự xây trong `yam.ds`; không dùng collection của `java.util` hoặc `Collections.sort`. Được dùng Random, Comparator, Iterator và NoSuchElementException. | Rà soát source không có ArrayList, LinkedList, Stack, ArrayDeque, HashMap hoặc collection tương đương thay thế cấu trúc được giao. | [G1] [N1] |
| DC-02 | Bàn cờ phải dùng `Cell[][]`, truy cập ô bằng hàng và cột O(1); danh sách hàng xóm dùng MyLinkedList và hai mảng offset. | Ô góc có 3, cạnh có 5, giữa có 8 hàng xóm; không lấy chính ô hoặc tọa độ ngoài bàn. | [G1] [N1] |
| DC-03 | MyLinkedList phải giữ head, tail, size; hỗ trợ thêm đầu/cuối, thêm theo chỉ số, chèn có thứ tự, get, removeFirst, remove, clear, iterator và toString. Thêm đầu/cuối, removeFirst, size và isEmpty O(1); thao tác theo chỉ số O(n). | Chuỗi thêm/xóa ở đầu, giữa, cuối giữ đúng thứ tự và size; sau xóa phần tử cuối vẫn thêm lại được; chỉ số sai ném IndexOutOfBoundsException. | [G7] [N1] |
| DC-04 | MyStack phải bảo đảm LIFO; MyQueue bảo đảm FIFO với front/rear; thao tác cơ bản O(1). | Push 1,2,3 cho pop 3,2,1; enqueue 1,2,3 cho dequeue 1,2,3; pop/dequeue/peek rỗng ném NoSuchElementException; clear tạo trạng thái rỗng có thể tái sử dụng. | [G7] [N1] |
| DC-05 | MyLinkedList phải cài Iterable; chèn addSorted phải ổn định với phần tử bằng nhau. | For-each trên list rỗng chạy 0 lần; next khi hết ném NoSuchElementException; dữ liệu cùng khóa A,B,C giữ thứ tự A,B,C. | [G7] [N1] |
| DC-06 | Phải có cả flood fill BFS bằng MyQueue và DFS bằng MyStack, đều dạng lặp; reveal trước enqueue/push, không dùng đệ quy. | Hai thuật toán trên bản sao cùng board mở đúng cùng tập tọa độ; ô cờ và mìn không mở; một ô không được thêm hai lần; thời gian O(N) ở trường hợp xấu nhất. | [G1] [N1] |
| DC-07 | Đặt mìn phải dùng Fisher–Yates trên các ô ứng viên; bảng xếp hạng giữ một MyLinkedList luôn sắp theo điểm bằng addSorted. | Đặt đúng số mìn không trùng và ngoài vùng an toàn; không dùng random thử lại đến khi trúng ô trống; score mới được chèn đúng vị trí rồi cắt quá 10 mục mỗi độ khó, không sort lại cả list. | [G1] [N1] |
| DC-08 | Phải dùng Java Swing, JDK 21 trở lên, UTF-8 cho source, main class yam.Main và quy trình build Ant của dự án NetBeans. | `ant clean jar` tạo `dist/yam.jar`; `java -jar dist/yam.jar` mở được ứng dụng trên Java runtime tương thích; source/target trong project là 21. | [G1] [G8] |

Không mở rộng DC-03 và DC-05 thành yêu cầu iterator fail-fast hoặc xử lý null đặc biệt: nguồn chưa xác định. Độ phức tạp là thuộc tính thuật toán; không thay thế bằng các số đo mili giây chưa kiểm chứng.

## 10 Yêu cầu phi chức năng

Mức Cơ sở có nội dung xác định từ tài liệu hoặc hợp đồng trong source. Mức Đề xuất là tiêu chuẩn bổ sung để nhóm phê duyệt trước khi dùng làm điều kiện nghiệm thu.

| Mã | Yêu cầu và cách xác minh | Cơ sở |
| --- | --- | --- |
| NFR-01 | Ứng dụng phải sử dụng được trên môi trường desktop có Java runtime tương thích bytecode Java 21 và Swing. Nghiệm thu tối thiểu bằng build JDK21 và chạy JAR ngoài NetBeans. Danh sách hệ điều hành hỗ trợ chính thức do nhóm chốt. | Cơ sở [G1] [G8] |
| NFR-02 | Dữ liệu điểm phải tồn tại qua các lần chạy bằng leaderboard.txt trong working directory; không có file được hiểu là bảng rỗng. Kiểm tra save, đóng, mở lại và load. | Cơ sở [G1] [N1] |
| NFR-03 | Một dòng điểm hỏng không được làm sập game hoặc làm mất khả năng đọc các dòng hợp lệ tiếp theo. Lỗi đọc/ghi phải được biểu diễn bằng kết quả thất bại của API. Kiểm tra file trộn dòng tốt và xấu, cùng đường dẫn không truy cập được. | Cơ sở [N1] và hợp đồng Leaderboard |
| NFR-04 | Các đối tượng Swing phải được tạo trên Event Dispatch Thread. Ô đã mở phải tiếp tục nhận sự kiện để chord hoạt động. Kiểm tra luồng khởi tạo và click lên ô số đã mở. | Cơ sở [N1] |
| NFR-05 | Timer hiển thị phải được làm mới theo CLOCK_REFRESH_MS=200 ms; giá trị nguyên giây tối đa 999 và đóng băng sau kết thúc. Đây là chu kỳ cấu hình, không phải cam kết độ chính xác 200 ms trong mọi tải. | Cơ sở hợp đồng Game và Constants |
| NFR-06 | Logic phải cho phép kiểm thử tái lập bằng Random(seed). Cùng độ khó, seed, ô mở đầu và cấu hình an toàn phải cho cùng bố trí mìn. | Cơ sở [N1] |
| NFR-07 | Việc thay đổi public signature phải được thống nhất với các module đang gọi; mỗi tệp giữ owner theo bảng phân công. Constants do TV2 quản lý. Xác minh bằng rà soát PR và danh sách file thay đổi. | Cơ sở [N1] |
| NFR-08 | Bản nộp phải hoàn tất M1 đến M5, không còn TODO chức năng hoặc UnsupportedOperationException dùng làm placeholder; xóa sandbox sau khi tích hợp và chơi thử đủ ba độ khó. | Cơ sở [N1] |
| NFR-09 | Trên máy nghiệm thu đã ghi rõ OS, CPU, RAM và JDK, 95% thao tác reveal, flag và chord của ba độ khó chuẩn nên cập nhật view trong 200 ms; không thao tác nào quá 1 s, loại thời gian người dùng xử lý hộp thoại. Đo ít nhất 100 thao tác gồm flood fill lớn. | Đề xuất về độ phản hồi |
| NFR-10 | File leaderboard nên đọc/ghi UTF-8; tên tiếng Việt phải roundtrip đúng; sanitizeName nên loại CR và LF ngoài dấu chấm phẩy để giữ một bản ghi trên một dòng. Kiểm tra tên `Nguyễn An` và tên có newline. | Đề xuất về toàn vẹn dữ liệu; source mới quy định source encoding và loại dấu chấm phẩy |
| NFR-11 | Khi save thất bại, GUI nên thông báo dễ hiểu, giữ điểm trong phiên hiện tại và cho phép chơi tiếp; không báo rằng điểm đã lưu thành công. Kiểm tra đường dẫn không ghi được. | Đề xuất xử lý lỗi GUI; source mới có kết quả false của API |
| NFR-12 | Trong phạm vi chơi cục bộ, ứng dụng không nên yêu cầu đăng nhập hay gửi tên/điểm qua mạng. Kiểm tra chơi mới, kết thúc ván và xem bảng điểm khi máy không có mạng. | Đề xuất chốt phạm vi offline từ mô hình lưu cục bộ |

## 11 Bộ kiểm thử nghiệm thu

Đây là đặc tả kiểm thử, chưa có kết quả thực thi. Dùng fixture kiểm soát bố trí mìn khi cần; không suy ra ô mìn từ seed khi chưa cố định ô click đầu. Các tọa độ bắt đầu từ 0. Cột Truy vết liên kết đến mã yêu cầu trong bản SRS này.

| Mã | Đầu vào hoặc bước kiểm tra | Kết quả mong đợi | Truy vết |
| --- | --- | --- | --- |
| AT-01 | Tạo MyLinkedList, MyStack và MyQueue mới, sau đó clear hai lần. | Size 0, isEmpty true, có thể thêm phần tử sau clear. | DC-03 DC-04 |
| AT-02 | Stack push 1,2,3; peek; pop ba lần. | Peek 3 không xóa; pop 3,2,1; size giảm đúng. | DC-04 |
| AT-03 | Queue enqueue 1,2,3; peek; dequeue ba lần; enqueue 4. | Peek 1; dequeue 1,2,3; phần tử 4 vào cấu trúc rỗng hợp lệ. | DC-04 |
| AT-04 | Pop, dequeue, peek trên cấu trúc rỗng; removeFirst list rỗng. | Ném NoSuchElementException, không làm hỏng cấu trúc. | DC-03 DC-04 |
| AT-05 | List add ở 0, giữa, size; get và remove ở đầu/giữa/cuối. | Thứ tự phần tử đúng; size không lệch; tail đúng sau xóa cuối. | DC-03 |
| AT-06 | Add tại -1 và size+1; get/remove tại -1 và size. | Từng lời gọi ném IndexOutOfBoundsException. | DC-03 |
| AT-07 | AddSorted các phần tử có khóa 3,1,2A,2B,2C. | Thứ tự 1,2A,2B,2C,3; không đảo mục bằng khóa. | DC-05 |
| AT-08 | For-each trên list rỗng và list [a,b]; next sau khi hết. | Rỗng không lặp; đầy cho a,b; next hết ném NoSuchElementException. | DC-05 |
| AT-09 | Tạo ba độ khó từ cấu hình. | Beginner 9x9/10; Intermediate 16x16/40; Expert 16x30/99; ô an toàn 71/216/381. | FR-02 |
| AT-10 | Board(0,9,1), Board(9,0,1), Board(2,2,0), Board(2,2,4); getCell(-1,0) và getCell(rows,0) trên bàn hợp lệ. | Constructor sai ném IllegalArgumentException; getCell sai ném IndexOutOfBoundsException; mineCount hợp lệ là 1..N-1. | FR-20 |
| AT-11 | Board 9x9: hàng xóm (0,0), (0,4), (4,4). | Lần lượt 3,5,8 ô; không có chính ô hoặc tọa độ ngoài biên. | FR-06 DC-02 |
| AT-12 | Reveal một ô an toàn hai lần; flag ô khác rồi reveal. | revealedCount chỉ tăng 1 cho lần mở thực sự; ô cờ không mở. | FR-07 FR-08 |
| AT-13 | Dùng Random(42), cùng board/click (4,4) cho hai lần tạo. | Cùng bố trí mìn và số liền kề; đủ M mìn. | FR-04 FR-05 NFR-06 |
| AT-14 | Tạo 1000 bàn Expert với ô mở đầu hợp lệ ở góc/cạnh/giữa. | Mỗi bàn đúng 99 mìn; không mìn trong vùng an toàn; mọi số liền kề đúng. | FR-04 FR-05 FR-06 |
| AT-15 | Board 2x2, 3 mìn, click đầu (0,0), bật safe-neighbors. | Hạ vùng an toàn còn ô click; đặt đủ 3 mìn; ô click an toàn. | FR-04 FR-05 |
| AT-16 | Ván READY, đợi rồi cắm/gỡ cờ, click ô cờ. | Giữ READY, elapsed 0, chưa đặt mìn. | FR-03 FR-08 FR-09 FR-17 |
| AT-17 | READY, reveal ô ẩn không cờ hợp lệ trên fixture bảo đảm còn ô an toàn ẩn sau mở đầu. | Đặt mìn đúng một lần; chuyển PLAYING; bắt đầu timer; click an toàn. | FR-03 FR-04 FR-05 FR-17 |
| AT-18 | PLAYING, reveal ô có số 2. | Chỉ ô đó mở nếu trước đó ẩn; hiện số 2. | FR-07 |
| AT-19 | Reveal ô zero ở fixture gồm vùng zero HIDDEN, biên số, ô cờ và zero đã REVEALED trước thao tác. | Mở vùng HIDDEN có thể đến được và biên số; không đi qua cờ hoặc zero đã mở từ trước; cờ và mìn không mở. | FR-11 |
| AT-20 | BFS/DFS trên hai bản sao cùng fixture; so sánh tập ô mở. | Tập tọa độ và số ô mới bằng nhau; mở lại trả 0. | FR-11 FR-12 DC-06 |
| AT-21 | Toggle cờ ô ẩn hai lần, rồi toggle ô đã mở. | HIDDEN→FLAGGED→HIDDEN; ô đã mở không đổi. | FR-09 |
| AT-22 | Beginner cắm 12 cờ hợp lệ. | flagsPlaced 12; counter -2; vẫn cho cắm thêm ô ẩn. | FR-09 FR-10 |
| AT-23 | Chord ô số 2 với 1 hoặc 3 cờ lân cận. | Không mở hàng xóm và không đổi trạng thái ván. | FR-13 |
| AT-24 | Chord ô số 2 với 2 cờ đúng và các ô ẩn an toàn. | Mở mọi hàng xóm ẩn không cờ, kèm flood fill nếu gặp zero. | FR-13 |
| AT-25 | Chord ô số 2 có đủ 2 cờ nhưng một cờ sai. | Mở trúng mìn dẫn LOST và dừng mở các ô tiếp theo. | FR-14 FR-16 |
| AT-26 | Chord ô ẩn, ô cờ, zero hoặc ván READY. | Bỏ qua; không tự khởi động game. | FR-03 FR-13 |
| AT-27 | Mở hết N-M ô an toàn, chưa cắm đủ cờ. | WON; mìn ẩn tự cắm cờ; counter 0; thời gian đóng băng. | FR-15 |
| AT-28 | Reveal một mìn sau click đầu, đồng thời có cờ sai. | LOST; ô nổ exploded true; hiện mìn không cờ; cờ sai phân biệt. | FR-16 FR-24 |
| AT-29 | Reveal/flag/chord sau WON hoặc LOST; gọi tọa độ ngoài biên khi đang chơi. | Bỏ qua, không đổi score, board hoặc thời gian. | FR-08 FR-19 FR-20 |
| AT-30 | READY rồi PLAYING; kiểm tra trước/sau 999 s và sau kết thúc. | READY 0; đếm nguyên giây; chặn 999; không tự thua; kết thúc giữ nguyên. | FR-17 FR-18 FR-26 |
| AT-31 | Một độ khó có 9 điểm; kiểm tra qualifies với 999. | True vì chưa đủ 10 mục. | FR-29 |
| AT-32 | Đủ 10 điểm, mục cuối 100 s; qualifies 99,100,101. | True, false, false theo thứ tự. | FR-29 |
| AT-33 | Thêm 15 điểm Beginner và điểm các mức khác. | Beginner giữ 10 tốt nhất; quota mức khác không giảm. | FR-30 |
| AT-34 | Điểm cùng thời gian, ngày khác; rồi cùng ngày/thời gian. | Ngày sớm trước; bằng cả hai giữ thứ tự chèn. | FR-29 FR-30 DC-05 |
| AT-35 | Tên null, trống, khoảng trắng, ` A;B ` và tên dài 25 ký tự. | Anonymous; Anonymous; Anonymous; AB; tên tối đa 20 ký tự sau làm sạch. | FR-28 |
| AT-36 | Parse dòng `An;BEGINNER;42;2026-10-22`; serialize lại. | Bốn trường hợp lệ được khôi phục và ghi đúng định dạng. | FR-31 |
| AT-37 | Parse dòng thiếu trường, enum lạ, số `x`, ngày không hợp lệ. | Từng dòng ném IllegalArgumentException. | FR-31 |
| AT-38 | Load đường dẫn chưa có file; file gồm 2 dòng tốt xen 1 dòng hỏng. | Trường hợp đầu true/rỗng; trường hợp sau giữ 2 dòng tốt, bỏ dòng hỏng. | FR-32 |
| AT-39 | Save bảng nhiều mức; tạo Leaderboard mới và load cùng file. | Tên, mức, thời gian, ngày và thứ tự được giữ. | FR-33 |
| AT-40 | Load/save đường dẫn hiện có nhưng không đọc/ghi được; clear rồi save. | Lỗi I/O trả false; clear khi chưa save không đổi file; save thành công ghi dữ liệu đã clear; không cam kết file cũ nguyên vẹn nếu ghi lỗi. | FR-30 FR-32 FR-33 NFR-03 |
| AT-41 | Khởi động ứng dụng. | MainFrame tạo trên EDT; Beginner; counter 010, timer 000, mặt :). | FR-21 |
| AT-42 | Click trái/phải/giữa một ô; click trái ô số đã mở. | Gọi đúng reveal/toggleFlag/chord; click trái trên số thực hiện chord. | FR-23 |
| AT-43 | Đang chơi Expert; nhấn mặt, F2 và Game New ở các ván riêng. | Tạo ván mới Expert; cờ/timer reset; không ghi điểm ván bỏ dở. | FR-01 FR-22 |
| AT-44 | Chọn Beginner, Intermediate rồi Expert từ Game. | Lưới đúng 9x9,16x16,16x30; menu chỉ chọn một mức; cửa sổ pack phù hợp. | FR-02 FR-22 |
| AT-45 | Render ô ẩn, cờ, số, mìn, ô nổ và cờ sai. | Trạng thái phân biệt đúng; ô đã mở không disable, số không bị xám. | FR-24 |
| AT-46 | Header nhận 7,-5,1500 và ba trạng thái READY/WON/LOST. | 007,-05,999; mặt :),B),X( tương ứng, PLAYING cũng :). | FR-25 |
| AT-47 | Thắng đạt top rồi click/timer nhiều lần; nhập trống hoặc Cancel. | Chỉ một lần hỏi tên và một score; dùng Anonymous; save rồi mở bảng điểm. | FR-19 FR-27 FR-28 |
| AT-48 | Thắng không đạt top; sau đó thua ở ván khác. | Không hỏi tên và không ghi score cho cả hai trường hợp. | FR-27 FR-29 |
| AT-49 | Mở Best Times, How to Play và About. | Ba tab độ khó, top đúng và bảng chỉ đọc; hai hộp Help hiển thị nội dung; có thể đóng quay lại game. | FR-34 FR-35 |
| AT-50 | Build clean JAR, chạy từ thư mục mới, chơi thử ba mức, lưu điểm và chạy lại; chọn Exit. | JAR khởi động đúng, bảng điểm tồn tại đúng working directory, Exit kết thúc ứng dụng. | DC-08 FR-21 FR-33 FR-35 |

Cần ghi lại trạng thái Pass/Fail, phiên bản build, môi trường, dữ liệu và bằng chứng cho từng ca khi thực thi. Không dùng bảng này như báo cáo đã kiểm thử.

### Kiểm tra bổ sung để phủ trường hợp biên

AT-54, AT-56, AT-57, AT-59 và AT-60 có phần nội dung đề xuất; chỉ chấm bắt buộc sau khi nhóm chốt OI hoặc NFR tương ứng. AT-29 chỉ yêu cầu chord ngoài biên bỏ qua sau khi FR-20 được chấp thuận; reveal và toggleFlag ngoài biên là yêu cầu nguồn.

| Mã | Đầu vào hoặc bước kiểm tra | Kết quả mong đợi | Truy vết |
| --- | --- | --- | --- |
| AT-51 | Dựng bản đồ biết trước vị trí mìn và cờ; tính số cho mọi ô an toàn và đếm cờ quanh góc, cạnh, giữa. | Số mìn lân cận khớp kiểm đếm độc lập; thay cờ chỉ thay đếm cờ, không thay số mìn. | FR-06 |
| AT-52 | Fixture A cắm đúng mọi mìn nhưng còn ô an toàn ẩn. Fixture B có một cờ sai trên ô an toàn; bỏ cờ đó rồi mở hết ô an toàn. | Cắm đúng mìn không tự thắng; chỉ mở đủ ô an toàn mới WON. | BR-01 FR-10 FR-15 |
| AT-53 | Đưa -100 vào formatThreeDigits và cắm nhiều cờ trên Expert. | Nhãn chặn -99; giá trị logic vẫn mineCount trừ flagsPlaced. | FR-10 FR-25 |
| AT-54 | Nhập tên tiếng Việt, ` ; ; `, `; A ;`, CR LF và chuỗi có emoji ở vị trí cắt 20. | Theo chính sách được duyệt: hai tên đặc biệt thành Anonymous và A; tên không tách record hoặc hỏng Unicode. | FR-28 OI-02 NFR-10 |
| AT-55 | Nhận list từ getTopScores rồi clear/remove; nạp lại cùng file hai lần. | Sửa cấu trúc list trả về không đổi entries; load không nhân đôi dữ liệu. | FR-30 FR-32 |
| AT-56 | Thêm dòng thời gian -1 và 1000 cùng các dòng hợp lệ; save-load tên tiếng Việt. | Theo đề xuất: bỏ dòng ngoài miền; UTF-8 roundtrip đúng tên và thứ tự. | FR-31 FR-32 OI-01 OI-02 |
| AT-57 | Giả lập lỗi đọc giữa chừng và lỗi save; click sau ván thắng. | Theo chính sách duyệt: load không giữ phần đọc dở; save giữ điểm trong RAM, báo chưa lưu, không thêm trùng. | FR-27 FR-32 FR-33 OI-03 |
| AT-58 | Rà soát code, PR, owner, thay đổi public signature, caller, build sạch và danh mục tệp bản nộp. | Không collection cấm, đệ quy hoặc placeholder; thay signature đã phối hợp; sandbox đã bỏ; exception hợp đồng vẫn có. | DC-01 DC-06 DC-07 DC-08 NFR-07 NFR-08 |
| AT-59 | Trên máy nghiệm thu đã ghi cấu hình, đo ít nhất 100 thao tác gồm flood fill lớn. | Nếu duyệt NFR-09: ít nhất 95% cập nhật không quá 200 ms, tất cả không quá 1 s, trừ thời gian hộp thoại. | NFR-09 OI-08 |
| AT-60 | Chạy không mạng; mở Best Times và Help giữa ván rồi đóng. | Chơi và bảng local không cần mạng; nếu duyệt OI-05, thời gian vẫn tính suốt khoảng mở hộp thoại. | NFR-12 FR-26 OI-05 |

## 12 Truy nguyên yêu cầu

Ma trận dưới đây liên kết các FR với nguồn, đơn vị triển khai và bộ kiểm thử. Các AT là kế hoạch, không phải bằng chứng đã đạt. Ký hiệu TV là chủ sở hữu công việc theo N1; module phụ thuộc vẫn phải tham gia review khi hợp đồng thay đổi.

| Nhóm yêu cầu | Nội dung | Nguồn | Trách nhiệm | Kiểm thử chính |
| --- | --- | --- | --- | --- |
| FR-01 FR-02 | Ván mới và preset | G1 G2 G3 G4 N1 | TV2 TV3 TV4 | AT-09 AT-43 AT-44 |
| FR-03 | READY và khởi động trễ | G2 G3 N1 | TV2 TV3 | AT-16 AT-17 |
| FR-04 FR-05 | Vùng an toàn và đặt mìn | G1 G2 N1 | TV2 TV3 | AT-13 AT-14 AT-15 |
| FR-06 | Hàng xóm và số lân cận | G1 G2 N1 | TV2 | AT-11 AT-14 AT-51 |
| FR-07 FR-08 | Reveal và đếm ô | G2 G3 | TV2 TV3 | AT-12 AT-18 AT-29 |
| FR-09 FR-10 | Cờ và bộ đếm | G1 G2 G3 | TV2 TV3 | AT-16 AT-21 AT-22 |
| FR-11 FR-12 | Flood fill BFS và DFS | G1 G3 G7 N1 | TV1 TV3 | AT-19 AT-20 |
| FR-13 FR-14 | Chord và dừng khi nổ | G1 G2 G3 N1 | TV3 TV4 | AT-23 AT-24 AT-25 AT-26 |
| FR-15 | Thắng và tự cắm cờ | G1 G2 G3 | TV2 TV3 | AT-27 AT-52 |
| FR-16 | Thua và hiển thị mìn | G1 G2 G3 G4 | TV2 TV3 TV4 | AT-28 AT-45 |
| FR-17 FR-18 | Thời gian và bão hòa | G1 G3 G6 | TV3 TV4 | AT-30 |
| FR-19 FR-20 | Chặn sau kết thúc và biên API | G2 G3 N1 | TV2 TV3 TV4 | AT-10 AT-29 AT-47 |
| FR-21 FR-22 | Khởi động và menu | G1 G4 G6 | TV4 | AT-41 AT-43 AT-44 AT-50 |
| FR-23 | Điều phối chuột | G1 G4 N1 | TV4 | AT-42 |
| FR-24 FR-25 FR-26 | Ô, header và nhịp đồng hồ | G4 G6 N1 | TV4 TV5 | AT-30 AT-45 AT-46 AT-53 |
| FR-27 FR-28 | Kết thúc một lần và tên | G4 G5 G6 | TV4 TV5 | AT-35 AT-47 AT-48 AT-54 |
| FR-29 FR-30 | Qualifies và danh sách top | G1 G5 G7 | TV1 TV5 | AT-31 AT-32 AT-33 AT-34 AT-55 |
| FR-31 FR-32 FR-33 | Định dạng và I/O | G1 G5 G6 | TV5 | AT-36 AT-37 AT-38 AT-39 AT-40 AT-56 AT-57 |
| FR-34 FR-35 | Bảng điểm, trợ giúp, thoát | G4 G5 | TV4 TV5 | AT-49 AT-50 |
| DC-01 đến DC-08 | Công nghệ và cấu trúc dữ liệu | G1 G7 G8 N1 | Cả nhóm theo owner | AT-01 đến AT-08 AT-20 AT-50 AT-58 |
| NFR-01 đến NFR-08 | Tính vận hành và kiểm chứng cơ sở | G1 G3 G4 G5 G8 N1 | Cả nhóm | AT-13 AT-30 AT-38 AT-40 AT-41 AT-50 AT-58 |
| NFR-09 đến NFR-12 | Mức chất lượng đề xuất | Đề xuất SRS | Cả nhóm phê duyệt | AT-54 AT-56 AT-57 AT-59 AT-60 |

Các kiểm thử có nội dung đề xuất chỉ được dùng làm cổng nghiệm thu sau khi OI tương ứng được chốt. Không coi kiểm thử cấu trúc DS đơn thuần là đủ để khẳng định luật chơi đúng; cần kiểm thử tích hợp Game và GUI theo cùng phiên bản.

## 13 Các quyết định cần chốt và rủi ro

### Danh mục quyết định mở

| Mã | Nội dung cần chốt | Đề xuất đường cơ sở | Tác động nếu thay đổi |
| --- | --- | --- | --- |
| OI-01 | Xử lý ván dài hơn 999 giây và điểm import ngoài khoảng | Giữ Game cap 999 như nguồn; đề xuất parser chỉ nhận 0 đến 999 | Dùng thời gian thật riêng sẽ đổi Game, ScoreEntry, format và test |
| OI-02 | Encoding, CR/LF và cách giới hạn tên Unicode | UTF-8; bỏ CR LF và ký tự điều khiển; trim lại sau loại separator; chốt 20 code point | Cần cập nhật sanitizeName và kiểm thử tên tiếng Việt, emoji |
| OI-03 | Phản hồi GUI khi load/save false và load dở | Đọc lỗi thì báo và làm rỗng phần đọc dở; ghi lỗi thì giữ RAM, báo chưa lưu | Ảnh hưởng MainFrame, load, save và thông điệp; nguồn chỉ chốt boolean |
| OI-04 | Ghi tệp tạm và thay thế có kiểm soát | Nên áp dụng nếu thời gian cho phép; không mặc định đã có | Tăng tin cậy nhưng thêm xử lý lỗi thay thế và test |
| OI-05 | Đồng hồ khi mở Best Times hoặc Help giữa ván | Tiếp tục tính vì không có PAUSED | Nếu pause cần bổ sung trạng thái, tổng thời gian và chính sách chống lợi dụng |
| OI-06 | Chord với tọa độ sai ở API | Bỏ qua như reveal và toggleFlag | Làm rõ guard trước getCell; test biên riêng |
| OI-07 | Nhánh tích hợp của nhóm | Dùng master theo repo hiện tại, hoặc đổi thống nhất sang main | N1 ghi main; cần cập nhật quy trình, không tự đổi nhánh trong SRS |
| OI-08 | Môi trường được cam kết hỗ trợ và ngưỡng phản hồi | Chốt OS và máy nghiệm thu; lấy NFR-09 làm mục tiêu thử | Không tuyên bố đa nền tảng hay đạt 200 ms khi chưa đo |
| OI-09 | Nhấn lại radio độ khó đang chọn | Đề xuất tạo ván mới nhất quán với chọn độ khó | Cần test listener và thông báo cách chơi |
| OI-10 | Thay đổi giờ hệ thống trong ván | Giữ cách đo nguồn cho bản học phần; nếu cần, bổ sung cách đo đơn điệu | System.currentTimeMillis có thể nhảy khi giờ hệ thống đổi; không hứa độ chính xác ngoài điều kiện chuẩn |

Nhóm ghi người quyết định, ngày và lựa chọn vào lịch sử phiên bản khi chốt. Những thay đổi mở rộng tính năng như pause hoặc thời gian thực vượt 999 phải có mã yêu cầu và cập nhật ca nghiệm thu; không xử lý âm thầm như sửa lỗi triển khai.

### Rủi ro kỹ thuật

| Rủi ro | Hậu quả | Biện pháp và bằng chứng cần có |
| --- | --- | --- |
| Cell.reveal được gọi trực tiếp trong flood fill | revealedCount sai, không thắng hoặc thắng nhầm | Chỉ mở an toàn qua Board.revealCell; kiểm thử đếm và điều kiện thắng |
| Đưa ô vào queue/stack trước khi đánh dấu | Thêm lặp, mở trùng hoặc tốn bộ nhớ | Reveal trước enqueue/push; so tập ô và số lượt thêm |
| Disable ô số đã mở | Không nhận chord, màu số sai | Giữ enabled và kiểm tra trạng thái ở Game |
| MainFrame xử lý game over lặp | Hỏi tên nhiều lần hoặc trùng thành tích | Set gameOverHandled trước dialog; kiểm tra click và refresh lặp |
| addSorted không ổn định | Sai thứ tự cùng thời gian và ngày | Test khóa trùng có nhãn riêng và save-load |
| Không cập nhật tail/rear khi cấu trúc rỗng | Mất phần tử hoặc lỗi vòng đời cấu trúc | Test thêm lại sau remove cuối và clear |
| Sai trim khi danh sách có nhiều độ khó xen kẽ | Xóa điểm mức khác hoặc bỏ sót mục dư | Test 15 điểm mỗi độ khó, xen kẽ và kiểm tra toàn danh sách |
| Lỗi đọc hoặc ghi tệp bị bỏ qua | Người chơi tưởng điểm đã lưu | Kiểm tra boolean và thông báo đề xuất theo OI-03 |
| Thay public signature không phối hợp | Lỗi tích hợp giữa các phần | Review các caller và owner trước thay đổi, build sạch sau merge |

## 14 Tích hợp và tiêu chí bàn giao

### Trách nhiệm triển khai

| Thành viên | Tệp sở hữu | Trách nhiệm SRS chính | Phụ thuộc |
| --- | --- | --- | --- |
| TV1 | ds/Node.java, MyLinkedList.java, MyStack.java, MyQueue.java | DC-01 DC-03 DC-04 DC-05 và nền cho FR-12 FR-30 | Không có phụ thuộc module game |
| TV2 | model/Cell.java, CellState.java, GameState.java, Difficulty.java, Board.java; logic/MinePlacer.java; util/Constants.java | FR-02 FR-04 FR-05 FR-06 FR-20 và bất biến Board | TV1 |
| TV3 | logic/Game.java, FloodFill.java | FR-01 đến FR-20 về điều phối luật và thuật toán | TV1 TV2 |
| TV4 | Main.java; ui/MainFrame.java, BoardPanel.java, CellButton.java, CellActionListener.java | FR-21 FR-22 FR-23 FR-24 FR-26 FR-27 FR-35 | TV2 TV3 TV5 |
| TV5 | score/ScoreEntry.java, Leaderboard.java; ui/LeaderboardDialog.java, HeaderPanel.java | FR-25 FR-28 đến FR-34 | TV1 TV2 |

Tên tệp trong bảng là đường dẫn dưới src/yam. Constants có owner TV2; người cần thay đổi phải phối hợp trước. README và tài liệu phân công chỉ sửa sau thống nhất, mỗi lần một người. Không tự sửa nbproject, build.xml hay manifest.mf khi chưa báo nhóm. Khi phát hiện lỗi ở tệp của thành viên khác, báo owner hoặc tạo issue theo quy trình nhóm.

### Thứ tự mở khóa và mốc tích hợp

| Mốc | Nội dung | Cổng hoàn thành |
| --- | --- | --- |
| M1 | TV1 hoàn thành Queue, Stack, iterator và thao tác list cơ bản; TV2 hoàn thành Difficulty, Cell, GameState.isOver và Board cần thiết | Các caller nhận đúng signature và chạy được kiểm tra cơ bản; merge các phần phụ thuộc sớm |
| M2 | Mỗi phần hoạt động độc lập qua sandbox của từng TV | DS, model, luật, GUI mẫu và score có kết quả tự kiểm tra riêng |
| M3 | Tích hợp TV3 và TV4 | Khởi động, reveal, flood fill, flag, chord, thắng, thua và restart chơi được |
| M4 | Tích hợp TV5 | Header phản ánh model; ghi, nạp và hiển thị top 10 hoạt động |
| M5 | Hoàn thiện toàn bộ | Chơi thử cả ba mức; hoàn tất yêu cầu cơ sở; xử lý lỗi đã ghi; xóa sandbox và placeholder |

Các nhánh cá nhân theo N1 có thể là tv1-ds, tv2-model, tv3-logic, tv4-ui, tv5-score. Merge qua PR vào nhánh tích hợp đã chốt ở OI-07. Mỗi thành viên dùng sandbox/TvXCheck.java của mình khi phát triển, không dùng thư mục đó như API sản phẩm.

### Định nghĩa hoàn thành

Bản bàn giao phải build sạch từ source bằng quy trình công bố, chạy được JAR ngoài IDE và chơi hết luồng ở ba preset. Tất cả FR cơ sở, DC và NFR cơ sở phải có kết quả kiểm tra và lỗi được quản lý. Các mục đề xuất đã chấp thuận phải được đưa vào cùng cổng kiểm thử; mục chưa chấp thuận phải giữ trạng thái mở, không đánh dấu đạt mặc định.

Bản nộp không còn TODO chức năng hoặc UnsupportedOperationException dùng làm placeholder. Cần phân biệt placeholder với ngoại lệ hợp đồng hợp lệ của DS và Board. Không xóa kiểm tra ngoại lệ đúng chỉ để đạt điều kiện “không còn placeholder”. Sandbox phải được loại khỏi bản hoàn thiện theo N1; bằng chứng kiểm thử nên được lưu trong báo cáo hoặc PR trước khi xóa.

Hồ sơ bàn giao gồm source đúng commit, hướng dẫn JDK và chạy, định dạng bảng điểm, kết quả AT và kết luận các OI đã chốt. Trạng thái chấp nhận cuối cùng phải nêu số ca đạt, thất bại, chưa chạy và lý do miễn trừ có người chấp thuận. Bảng kiểm thử trong SRS này khởi đầu ở trạng thái chưa thực thi.

## 15 Thuật ngữ

| Thuật ngữ | Ý nghĩa |
| --- | --- |
| YAM | Yet Another Minesweeper, tên dự án |
| SRS | Đặc tả yêu cầu phần mềm dùng làm căn cứ triển khai và nghiệm thu |
| Reveal | Mở một ô ẩn hợp lệ |
| Flag | Đánh dấu hoặc bỏ đánh dấu ô nghi có mìn |
| Chord | Mở các hàng xóm ẩn quanh ô số đã mở khi có đủ số cờ |
| Flood fill | Lan mở vùng ô 0 và các ô số biên an toàn |
| BFS | Duyệt theo bề rộng dùng hàng đợi FIFO |
| DFS | Duyệt theo chiều sâu dùng ngăn xếp LIFO |
| Fisher–Yates | Cách xáo chọn ứng viên để đặt mìn không trùng |
| Sorted insertion | Chèn phần tử vào đúng vị trí của danh sách đã sắp |
| Stable | Phần tử bằng khóa giữ thứ tự chèn |
| EDT | Event Dispatch Thread xử lý giao diện Swing |
| Preset | Cấu hình độ khó cố định |
| Seed | Giá trị khởi tạo Random để tái lập cùng chuỗi ngẫu nhiên |
| Working directory | Thư mục hiện hành khi chạy tiến trình, nơi đặt leaderboard.txt |
| Fixture | Dữ liệu kiểm thử được kiểm soát để biết trước kết quả |
| Placeholder | Phần thân hàm tạm chưa có chức năng đích |

## 16 Phụ lục nguồn

Các liên kết dưới đây trỏ đến phiên bản đã dùng cho SRS. Khi review một thay đổi sau commit này, cần lập phiên bản tài liệu mới hoặc ghi rõ phạm vi chênh lệch.

- [G1 README của YAM](https://github.com/inthrephc/yam/blob/7456c382fa18564113509eafaf5f326c97b5894e/README.md)
- [G2 Mô hình bàn cờ](https://github.com/inthrephc/yam/tree/7456c382fa18564113509eafaf5f326c97b5894e/src/yam/model) và [MinePlacer](https://github.com/inthrephc/yam/blob/7456c382fa18564113509eafaf5f326c97b5894e/src/yam/logic/MinePlacer.java)
- [G3 Game](https://github.com/inthrephc/yam/blob/7456c382fa18564113509eafaf5f326c97b5894e/src/yam/logic/Game.java) và [FloodFill](https://github.com/inthrephc/yam/blob/7456c382fa18564113509eafaf5f326c97b5894e/src/yam/logic/FloodFill.java)
- [G4 Main](https://github.com/inthrephc/yam/blob/7456c382fa18564113509eafaf5f326c97b5894e/src/yam/Main.java) và [giao diện Swing](https://github.com/inthrephc/yam/tree/7456c382fa18564113509eafaf5f326c97b5894e/src/yam/ui)
- [G5 Thành tích](https://github.com/inthrephc/yam/tree/7456c382fa18564113509eafaf5f326c97b5894e/src/yam/score)
- [G6 Constants](https://github.com/inthrephc/yam/blob/7456c382fa18564113509eafaf5f326c97b5894e/src/yam/util/Constants.java)
- [G7 Cấu trúc dữ liệu](https://github.com/inthrephc/yam/tree/7456c382fa18564113509eafaf5f326c97b5894e/src/yam/ds)
- [G8 Cấu hình dự án](https://github.com/inthrephc/yam/blob/7456c382fa18564113509eafaf5f326c97b5894e/nbproject/project.properties) và [build Ant](https://github.com/inthrephc/yam/blob/7456c382fa18564113509eafaf5f326c97b5894e/build.xml)
- N1 Tài liệu phân công nhóm gồm Bảng phân công, Thứ tự ưu tiên của công việc, Quy trình làm việc chung, Mô tả công việc chi tiết và Các mốc tích hợp, do nhóm cung cấp cùng yêu cầu lập SRS.

Lịch sử phiên bản: 1.0 ngày 26 tháng 10 năm 2026, lập đặc tả từ đường cơ sở GitHub và yêu cầu nhóm; chờ rà soát và chốt các mục đề xuất. Người phê duyệt và tên thành viên thực tế sẽ do nhóm điền khi thông qua tài liệu.
