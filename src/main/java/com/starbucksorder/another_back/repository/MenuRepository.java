package com.starbucksorder.another_back.repository;

import com.starbucksorder.another_back.entity.MenuJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MenuRepository extends JpaRepository<MenuJpaEntity, Long> {

    // 메뉴 삭제 !
    @Modifying
    @Query(value = "CALL ps_delete_menu(:menuIds, @deletedCount)", nativeQuery = true)
    void deleteByMenuIds(@Param("menuIds") String menuIds);

    @Query(value = "SELECT @deletedCount", nativeQuery = true)
    int getDeletedCount();

    // 메뉴 상태 수정 !
    @Modifying
    @Query("""
            UPDATE MenuJpaEntity m 
            SET m.menuStatus =
            CASE WHEN m.menuStatus = 1 THEN 0 ELSE 1 END 
            WHERE m.menuId = :menuId
            """)
    int updateMenuStatus(@Param("menuId") Long menuId);
}
