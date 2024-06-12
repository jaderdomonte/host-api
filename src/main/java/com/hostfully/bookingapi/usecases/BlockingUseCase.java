package com.hostfully.bookingapi.usecases;

import com.hostfully.bookingapi.db.entity.BlockingEntity;
import com.hostfully.bookingapi.db.mapper.BlockingEntityDomainMapper;
import com.hostfully.bookingapi.db.repository.BlockingRepository;
import com.hostfully.bookingapi.db.repository.PropertyRepository;
import com.hostfully.bookingapi.db.validation.OverlappingValidation;
import com.hostfully.bookingapi.domain.Blocking;
import com.hostfully.bookingapi.exceptions.ResourceNotFoundException;
import com.hostfully.bookingapi.web.dto.PeriodDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BlockingUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(BlockingUseCase.class);

    private final BlockingRepository blockingRepository;

    private final PropertyRepository propertyRepository;

    private final OverlappingValidation overlappingValidation;

    private final BlockingEntityDomainMapper blockingEntityDomainMapper;

    public List<Blocking> getBlockingsByFilter(Long propertyId){
        LOG.info("Starting getting Blockings by filter");
        List<BlockingEntity> allBlockings = blockingRepository.findByFilter(propertyId);
        LOG.info("Returned {} Blockings", allBlockings.size());
        return allBlockings.stream().map(blocking -> blockingEntityDomainMapper.toDomain(blocking)).toList();
    }

    public void createBlocking(Blocking blocking){
        LOG.info("Starting creating Blocking");
        BlockingEntity entity = blockingEntityDomainMapper.toEntity(blocking);
        propertyRepository.findById(entity.getProperty().getId()).orElseThrow(() -> new ResourceNotFoundException("There is no Property with id: " + entity.getProperty().getId()));

        overlappingValidation.checkOverlappingBlocking(blocking.getId(), entity.getProperty().getId(), entity.getPeriod());
        overlappingValidation.checkOverlappingBooking(entity.getProperty().getId(), entity.getPeriod());

        blockingRepository.save(entity);
        LOG.info("Blocking created");
    }

    public void deleteBlocking(Long id){
        LOG.info("Starting deleting Blocking {}", id);
        BlockingEntity blockingEntity = blockingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Blocking with id: " + id));
        blockingRepository.delete(blockingEntity);
        LOG.info("Blocking {} deleted", id);
    }

    public void updateBlocking(Long id, PeriodDto period){
        LOG.info("Starting updating Blocking {}", id);
        BlockingEntity entity = blockingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Blocking with id: " + id));

        entity.getPeriod().setCheckIn(period.checkIn());
        entity.getPeriod().setCheckOut(period.checkOut());

        overlappingValidation.checkOverlappingBlocking(id, entity.getProperty().getId(), entity.getPeriod());
        overlappingValidation.checkOverlappingBooking(entity.getProperty().getId(), entity.getPeriod());

        blockingRepository.save(entity);
        LOG.info("Blocking {} updated", id);
    }
}
