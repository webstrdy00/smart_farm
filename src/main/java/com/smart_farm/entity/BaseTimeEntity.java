package com.smart_farm.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 직접적으로 데이터베이스 테이블과 매핑되는 테이블x
@EntityListeners(AuditingEntityListener.class) // 엔티티 클래스가 변화를 감지하는 리스너를 등록하는데 사용
public abstract class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
