// 추가된 파일들을 담아줄 배열. File객체로 하나씩 담음
	let uploadFiles = [];

	document.addEventListener("DOMContentLoaded", function(){
	     //input type=file이 변경되면 미리보기 동작
		document.getElementById("btnAtt").addEventListener("change", function(e) {
			//input type=file에 추가된 파일들을 변수로 받아옴
			const files = e.target.files;

			//변수로 받아온 파일들을 배열 형태로 변환
			const fileArr = Array.prototype.slice.call(files);
			
			//배열에 있는 파일들을 하나씩 꺼내서 처리
			for(f of fileArr) {
				imageLoader(f);
			}
		});
			
		document.getElementById("insertForm").addEventListener("submit", function() {
			//마지막으로 btnAtt에 uploadFiles에 있는 파일들을 담아준다.
			dt = new DataTransfer();
			
			for(f in uploadFiles) {
				const file = uploadFiles[f];
				dt.items.add(file);
			}
			
			document.getElementById("btnAtt").files = dt.files;
		});
	});
	
	function chkWidHeig(img, file) {
		img.setAttribute("style", "width: 100%; height: 100%; z-index: none;");
		//미리보기 영역에 추가
		//미리보기 이미지 태그와 삭제 버튼 그리고 파일명을 표출하는 p태그를 묶어주는 div 만들어서
		//미리보기 영역에 추가
		document.getElementById("attZone").append(makeDiv(img, file));
	}
		
	//미리보기 영역에 들어갈 img태그 생성 및 선택된 파일을 Base64 인코딩된 문자열 형태로 변환하여 미리보기
	//db나 서버에 있는 파일이 아니라서 인코딩 해줘야 함...
	function imageLoader(file) {
		let imgCnt = 0;
		let fileCnt = 0;
		
		for(let i=0; i<uploadFiles.length; i++) {
			console.log(uploadFiles[i]);
			if(uploadFiles[i].name.toLowerCase().match(/(.*?)\.(jpg|jpeg|png|gif|svg|bmp)$/)) {
				imgCnt++;
			} else {
				fileCnt++;
			}
		}
		
		
		if(imgCnt>2) {
			alert("이미지는 3개 초과해서 담을 수 없습니다.");
			return;
		} 

		if(fileCnt>3) {
			alert("파일은 3개 초과해서 담을 수 없습니다.");
			return;
		}
		
		uploadFiles.push(file);
		
		let reader = new FileReader();
		
		reader.onload = function(e) {
			//이미지를 표출해줄 img태그 선언
			let img = document.createElement("img");
			
			//이미지 파일인지 아닌지 체크
			if(file.name.toLowerCase().match(/(.*?)\.(jpg|jpeg|png|gif|svg|bmp)$/)) {
				var maxSize = 10 * 1024 * 1024;
		        var fileSize = f.size;
		        var maxImageWidth = 1024;
		        var maxImageHeight = 1024;
		
		        if(fileSize > maxSize){
		            alert("첨부파일 사이즈는 10MB 이내로 등록 가능합니다.");
		            return false;
		        }		        
		        
		        // 이미지 객체가 로드되면 실행되는 이벤트 리스너를 등록합니다.
			    img.onload = function() {
				    // 이미지의 가로 길이를 가져옵니다.
				    const width = this.width;
				    
				    // 이미지의 세로 길이를 가져옵니다.
				    const height = this.height;
				
				    // 가로, 세로 길이를 출력합니다.
				    if (width > maxImageWidth || height > maxImageHeight) {
		                alert("이미지의 가로, 세로 사이즈는 1024px x 1024px 이내로 등록 가능합니다.");
		                return false;
		            } else {
		            	chkWidHeig(img, file);
		            }
			    };
            	//이미지 파일 미리보기 처리
				img.src = e.target.result;
			} else {
				//일반 파일 미리보기 처리
				img.src = "/images/defaultFileImg.png";
				chkWidHeig(img, file);
			}			
		};
		
		//파일을 Base64 인코딩된 문자열로 변경
		reader.readAsDataURL(file);
	}

	//미리보기 영역에 들어가 div(img+button+p)를 생성하고 리턴
	function makeDiv(img, file) {
		//div 생성
		let div = document.createElement("div");
		div.setAttribute("style", "display: inline-block; position: relative;"
		+ " width: 150px; height: 120px; margin: 5px; border: 1px solid #00f; z-index: 1;");
		
		//button 생성
		let btn = document.createElement("input");
		btn.setAttribute("type", "button");
		btn.setAttribute("value", "x");
		btn.setAttribute("delFile", file.name);
		btn.setAttribute("style", "width: 30px; height: 30px; position: absolute;"
		+ " right: 0px; bottom: 0px; z-index: 999; background-color: rgba(255, 255, 255, 0.1);"
		+ " color: #f00;");
		
		//버튼 클릭 이벤트
		//버튼 클릭 시 해당 파일이 삭제되도록 설정
		btn.onclick = function(e) {
			//클릭된 버튼
			const ele = e.srcElement;
			//delFile(파일이름) 속성 꺼내오기: 삭제될 파일명
			const delFile = ele.getAttribute("delFile");
			
			for(let i = 0; i < uploadFiles.length; i++) {
				//배열에 담아놓은 파일들중에 해당 파일 삭제
				if(delFile == uploadFiles[i].name) {
					//배열에서 i번째 한개만 제거
					uploadFiles.splice(i, 1);
				}
			}
			
			//버튼 클릭 시 btnAtt에 첨부된 파일도 삭제
			dt = new DataTransfer();
			
			
			for(f in uploadFiles) {
				const file = uploadFiles[f];
				dt.items.add(file);
			}
			
			document.getElementById("btnAtt").files = dt.files;
			
			//해당 img를 담고있는 부모태그인 div 삭제
			const parentDiv = ele.parentNode;
			parentDiv.remove();
		}
		
		//파일명 표출할 p태그 생성
		const fName = document.createElement("p");
		fName.setAttribute("style", "display: inline-block; font-size: 8px;");
		fName.textContent = file.name;
		
		//div에 하나씩 추가
		div.appendChild(img);
		div.appendChild(btn);
		div.appendChild(fName);
		
		//완성된 div 리턴
		return div;
	}