package qianggou.utils;

import java.io.Serializable;

public class OrderRecord implements Serializable {
		private Long id;
		private String userId;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public OrderRecord(Long id, String userId) {
			super();
			this.id = id;
			this.userId = userId;
		}
		
}
