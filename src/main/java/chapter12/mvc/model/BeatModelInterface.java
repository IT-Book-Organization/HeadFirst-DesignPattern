package main.java.chapter12.mvc.model;
  
public interface BeatModelInterface {
	void initialize();
  
	void on();
  
	void off();
  
    void setBPM(int bpm);
  
	int getBPM();

	// 모델은 옵저버 패턴을 사용한다.
	void registerObserver(BeatObserver o);
  
	void removeObserver(BeatObserver o);
  
	void registerObserver(BPMObserver o);
  
	void removeObserver(BPMObserver o);
}
