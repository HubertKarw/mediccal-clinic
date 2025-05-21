package com.HubertKarw.medical_clinic.Validation;

import com.HubertKarw.medical_clinic.Exception.VisitDateException;
import com.HubertKarw.medical_clinic.Model.Doctor;
import com.HubertKarw.medical_clinic.Model.Visit;
import com.HubertKarw.medical_clinic.Repository.VisitJpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public class VisitJpaValidator {
    public static void validateCreationTime(Visit visit, VisitJpaRepository repository){
        timeFrameAlreadyTaken(visit.getVisitStart(),visit.getVisitEnd(), visit.getDoctor(), repository);
        visitTimeMoreThanZero(visit.getVisitStart(),visit.getVisitEnd());
        timeFrameInQuarterOfAnHour(visit.getVisitStart(),visit.getVisitEnd());
        visitIsInFuture(visit.getVisitStart());
    }

    private static void visitTimeMoreThanZero(LocalDateTime start, LocalDateTime end){
        if (end.isBefore(start) || end.isEqual(start)){
            throw new VisitDateException("Dates are not valid");
        }
    }

    private static void timeFrameInQuarterOfAnHour(LocalDateTime start, LocalDateTime end){
        if (start.getMinute()%15!=0){
            throw new VisitDateException("Start Date not in quarter of an Hour");
        }
        if (end.getMinute()%15!=0){
            throw new VisitDateException("End Date not in quarter of an Hour");
        }
    }

    private static void visitIsInFuture(LocalDateTime start){
        if (start.isBefore(LocalDateTime.now())){
            throw new VisitDateException("Cannot create visit in past");
        }
    }

    private static void timeFrameAlreadyTaken(LocalDateTime start, LocalDateTime end, Doctor doctor, VisitJpaRepository repository){
        List<Visit> overlappingVisits = repository.findOverlappingVisits(start,end,doctor);
        System.out.println(overlappingVisits);
        if (!overlappingVisits.isEmpty()){
            throw new VisitDateException("There are overlapping visits");
        }

    }
}
