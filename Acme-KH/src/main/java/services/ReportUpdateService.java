package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReportUpdateRepository;
import security.Authority;
import domain.Actor;
import domain.Administrator;
import domain.ContentManager;
import domain.Defense;
import domain.GameMaster;
import domain.Report;
import domain.ReportStatus;
import domain.ReportUpdate;

@Service
@Transactional
public class ReportUpdateService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ReportUpdateRepository reportUpdateRepository;
	@Autowired
	private ActorService actorService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private Validator validator;

	// CRUD methods

	public ReportUpdate create() {
		ReportUpdate reportUpdate;

		reportUpdate = new ReportUpdate();

		return reportUpdate;
	}

	public ReportUpdate save(ReportUpdate reportUpdate, Integer reportId) {
		Assert.notNull(reportUpdate);
		Report report;
		ReportUpdate saved;

		report = reportService.findOne(reportId);
		Assert.isTrue(report.getStatus() != ReportStatus.RESOLVED,
				"error.message.noUpdate");

		if (reportUpdate.getId() != 0) {
			Assert.isTrue(report.getReportUpdates().contains(reportUpdate),
					"error.message.distincReport");
		}
		saved = reportUpdateRepository.save(reportUpdate);

		if (reportUpdate.getId() == 0) {
			report.getReportUpdates().add(saved);
		}

		report.setStatus(saved.getStatus());
		reportService.save(report);

		return saved;
	}

	public ReportUpdate findOne(int reportUpdateId) {
		Assert.notNull(reportUpdateId);

		ReportUpdate ReportUpdate;

		ReportUpdate = reportUpdateRepository.findOne(reportUpdateId);

		return ReportUpdate;
	}

	public ReportUpdate findOneToEdit(int reportUpdateId) {
		Assert.notNull(reportUpdateId);
		Assert.isTrue(actorService.getPrincipalAuthority().equals(
				Authority.ADMIN)
				|| actorService.getPrincipalAuthority().equals(Authority.GM));
		Assert.isTrue(reportService.findReportsByReportUpdate(reportUpdateId)
				.getStatus() != ReportStatus.RESOLVED, "error.message.noUpdate");
		
		
		ReportUpdate reportUpdate;

		reportUpdate = reportUpdateRepository.findOne(reportUpdateId);
		
		Assert.isTrue(reportUpdate.getCreator().equals(actorService.findByPrincipal()),"error.message.notOwnUpdate");

		return reportUpdate;
	}

	public Collection<ReportUpdate> findAll() {
		Collection<ReportUpdate> ReportUpdates;

		ReportUpdates = reportUpdateRepository.findAll();

		return ReportUpdates;
	}

	public void delete(ReportUpdate ReportUpdate) {
		Assert.notNull(ReportUpdate);

		reportUpdateRepository.delete(ReportUpdate);
	}

	// Other business methods

	public Collection<ReportUpdate> getReportUpdatesByReportId(int reportId) {
		return reportUpdateRepository.getReportUpdatesByReportId(reportId);
	}

	public void markSuspicious(ReportUpdate reportUpdate) {
		Assert.notNull(reportUpdate);

		reportUpdate.setIsSuspicious(true);
		reportUpdateRepository.save(reportUpdate);
	}

	public Collection<ReportUpdate> getSuspiciousReportUpdates() {
		Assert.isTrue(actorService.getPrincipalAuthority().equals(
				Authority.ADMIN));

		return reportUpdateRepository.getSuspiciousReportUpdates();
	}

	public ReportUpdate reconstruct(ReportUpdate reportUpdate,
			final BindingResult binding) {
		Actor actor;

		actor = actorService.findByPrincipal();

		if (reportUpdate.getId() == 0) {
			if (actorService.getPrincipalAuthority().equals("ADMIN")) {
				reportUpdate.setAdministrator((Administrator) actor);
				reportUpdate.setGameMaster(null);
			} else if (actorService.getPrincipalAuthority().equals("GM")) {
				reportUpdate.setGameMaster((GameMaster) actor);
				reportUpdate.setAdministrator(null);
			}

			reportUpdate.setIsSuspicious(false);

		} else {
			final ReportUpdate original = this.findOneToEdit(reportUpdate
					.getId());

			if (original.getGameMaster() != null) {
				reportUpdate.setGameMaster(original.getGameMaster());
				reportUpdate.setAdministrator(null);
			} else if (original.getAdministrator() != null) {
				reportUpdate.setAdministrator(original.getAdministrator());
				reportUpdate.setGameMaster(null);
			}

			reportUpdate.setIsSuspicious(original.getIsSuspicious());

		}

		reportUpdate.setDate(new Date(System.currentTimeMillis() - 1000));

		this.validator.validate(reportUpdate, binding);

		return reportUpdate;
	}
	
	public Collection<ReportUpdate> getReportUpdatesByActorId(int reportId, int actorId){
		return reportUpdateRepository.getReportUpdatesByActorId(reportId, actorId);
	}
	
	public Collection<ReportUpdate> getResolvedReportUpdates(int reportId){
		return reportUpdateRepository.getResolvedReportUpdates(reportId);
	}

}