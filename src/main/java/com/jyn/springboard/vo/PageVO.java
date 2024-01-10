package com.jyn.springboard.vo;

public class PageVO {
   //표출할 첫 페이지의 번호
   private int startPage;
   //표출할 끝 페이지의 번호
   private int endPage;
   //이전버튼, 다음버튼 표출여부
   private boolean prev, next;
   //총 게시글의 개수
   private int total;
   //크리테리아 객체
   private Criteria cri;
   
   public PageVO(Criteria cri, int total) {
      this.cri = cri;
      this.total = total;
      
      //끝 페이지 계산
      this.endPage = (int)(Math.ceil(cri.getPageNum() / 10.0)) * 10;
      
      //시작 페이지 계산
      this.startPage = this.endPage - 9;
      
      //마지막 데이터가 있는 페이지                      
      int realEnd = (int)(Math.ceil((total * 1.0) / cri.getAmount()));
      
      if(realEnd < this.endPage) {
         this.endPage = realEnd;
      }
      
      //버튼표출 여부 판단
      this.prev = cri.getPageNum() > 1;
             
      this.next = cri.getPageNum() < this.endPage;
   }

   public int getStartPage() {
      return startPage;
   }

   public void setStartPage(int startPage) {
      this.startPage = startPage;
   }

   public int getEndPage() {
      return endPage;
   }

   public void setEndPage(int endPage) {
      this.endPage = endPage;
   }

   public boolean isPrev() {
      return prev;
   }

   public void setPrev(boolean prev) {
      this.prev = prev;
   }

   public boolean isNext() {
      return next;
   }

   public void setNext(boolean next) {
      this.next = next;
   }

   public int getTotal() {
      return total;
   }

   public void setTotal(int total) {
      this.total = total;
   }

   public Criteria getCri() {
      return cri;
   }

   public void setCri(Criteria cri) {
      this.cri = cri;
   }

   @Override
   public String toString() {
      return "PageVO [startPage=" + startPage + ", endPage=" + endPage + ", prev=" + prev + ", next=" + next
            + ", total=" + total + ", cri=" + cri + "]";
   }
   
   
}