package com.example.demo.heist;

import com.example.demo.heist.objects.Heist;
import com.example.demo.heist.objects.MemberHeist;
import com.example.demo.heist.objects.Members;
import com.example.demo.heist.objects.SkillHeist;
import com.example.demo.heist.viewHelp.GetHeistEligibleMembers;
import com.example.demo.heist.viewHelp.GetHeistSkills;
import com.example.demo.heist.viewHelp.GetHeistStatus;
import com.example.demo.member.MemberRepository;
import com.example.demo.member.MemberService;
import com.example.demo.member.objects.Member;
import com.example.demo.member.objects.SkillMember;
import com.example.demo.member.viewHelp.GetHeistMembers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HeistService {

    private static final Logger logger = LoggerFactory.getLogger(HeistService.class);

    private final HeistRepository heistRepository;
    private final MemberRepository memberRepository;
    private final MemberHeistRepository memberHeistRepository;

    public HeistService(HeistRepository heistRepository, MemberRepository memberRepository, MemberHeistRepository memberHeistRepository) {
        this.heistRepository = heistRepository;
        this.memberRepository = memberRepository;
        this.memberHeistRepository = memberHeistRepository;
    }

    public List<Heist> getHeists() {
        return heistRepository.findAll();
    }

    public void addNewHeist(Heist heist) {
        List<SkillHeist> skillsPost = heist.getSkills();

        Optional<Heist> heistOptional = heistRepository.findHeistByName(heist.getName());
        if (heistOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Heist pod tim imenom postoji");
        }

        Set<String> store = new HashSet<>();
        for (SkillHeist s : skillsPost) {
            if (!store.add(s.getName() + " " + s.getLevel())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unesen skill istog imena i levela");
            }
        }

        if (!heist.getStartTime().matches("[0-9]{4}-[0-9]{2}-[0-9]{2}[T][0-9]{2}[:][0-9]{2}[:][0-9]{2}[.][0-9]{3}[Z]")
                || !heist.getEndTime().matches("[0-9]{4}-[0-9]{2}-[0-9]{2}[T][0-9]{2}[:][0-9]{2}[:][0-9]{2}[.][0-9]{3}[Z]")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Krivi format datuma. Datum mora izgledati kao: yyyy-MM-ddTHH:mm:ss.SSSZ");
        }

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(heist.getStartTime());
            date2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(heist.getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date1 != null && date2 != null) {
            if (date1.after(date2)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "StartTime mora biti prije endTime");
            }
        }

        heist.setStatus("PLANNING");
        heistRepository.save(heist);
    }

    @Transactional
    public void updateHeistSkills(Heist heist, Long heist_id) {
        Set<String> store = new HashSet<>();
        Heist heist2 = heistRepository.findById(heist_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Heist navedenog id-a ne postoji"));
        List<SkillHeist> skillsUpdate = heist.getSkills();
        List<SkillHeist> skillsGet = heist2.getSkills();

        for (SkillHeist s : skillsUpdate) {
            if (!store.add(s.getName() + " " + s.getLevel())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unesen skill istog imena i levela");
            }
            for (SkillHeist get : skillsGet) {
                if (get.getName().equals(s.getName()) && get.getLevel().equals(s.getLevel())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unesen skill već postoji");
                }
            }
        }

        if (heist2.getStatus().equals("IN_PROGRESS")) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Heist je već započeo");
        }

        for (SkillHeist update : skillsUpdate) {
            skillsGet.add(new SkillHeist(update.getName(), update.getLevel(), update.getMembers()));
        }

        heist2.setSkills(skillsGet);
    }

    @Transactional
    public void confirmHeistMembers(Members members1, Long heist_id) {
        List<String> members = members1.getMembers();
        Heist heist = heistRepository.findById(heist_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Heist navedenog id-a ne postoji"));

        List<SkillHeist> skillsHeist = heist.getSkills();
        List<Member> memberList = new ArrayList<Member>();
        List<String> nameHeist = new ArrayList<String>();
        List<String> nameMember = new ArrayList<String>();

        if (!heist.getStatus().equals("PLANNING")) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Heist status nije PLANNING");
        }
        for (String m : members) {
            Member member = memberRepository.findMemberByName(m).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member ne postoji"));
            memberList.add(member);

            List<SkillMember> skillsMember = member.getSkills();
            for (SkillMember sm : skillsMember) {
                nameMember.add(sm.getName());
            }
        }
        for (Member member : memberList) {
            String status = member.getStatus();
            boolean equals1 = "AVAILABLE".equals(status);
            boolean equals2 = "RETIRED".equals(status);
            if (equals1 == true || equals2 == true) {
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member status mora biti AVAILABLE ili RETIRED");
            }
            //TODO if member is in another heist in same time -> BAD REQUEST
        }
        for (SkillHeist sh : skillsHeist) {
            nameHeist.add(sh.getName());
        }
        int occurrences = 0;
        for (String m : nameMember) {
            for (int i = 0; i < nameHeist.size(); i++) {
                if (nameHeist.get(i).equals(m)) {
                    occurrences = occurrences + 1;
                }
            }
        }
        if (occurrences < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member skill se ne podudara sa heist skill");
        }

        for (String m : members) {
            Member member = memberRepository.findMemberByName(m).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member ne postoji"));

            MemberHeist memberHeist = new MemberHeist();
            memberHeist.setHeist_id(heist_id);
            memberHeist.setMember_id(member.getId());
            memberHeistRepository.save(memberHeist);
            heist.setStatus("READY");
        }
    }

    @Transactional
    public void startHeist(Long heist_id) {
        Heist heist = heistRepository.findById(heist_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Heist navedenog id-a ne postoji"));
        if (!heist.getStatus().equals("READY")) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Heist status nije READY");
        }
        heist.setStatus("IN_PROGRESS");
    }

    public Heist getHeistById(Long heist_id) {
        return heistRepository.findById(heist_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Heist navedenog id-a ne postoji"));
    }

    public List<SkillHeist> getHeistSkillsById(Long heist_id) {
        GetHeistSkills heistById = heistRepository.findHeistById(heist_id);
        if (heistById == null || heistById.getSkills().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Heist navedenog id-a ne postoji");
        }
        return heistById.getSkills();
    }

    public GetHeistStatus getHeistStatusById(Long heist_id) {
        GetHeistStatus heistById = heistRepository.findHeistStatusById(heist_id);
        if (heistById == null || heistById.getStatus().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Heist navedenog id-a ne postoji");
        }
        return heistById;
    }

    public List<GetHeistMembers> getHeistMembers(Long heist_id) {
        Heist heist = heistRepository.findById(heist_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Heist navedenog id-a ne postoji"));
        if (!"PLANNING".equals(heist.getStatus())) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Heist status nije PLANNING");
        }
        List<GetHeistMembers> getHeistMembers = new ArrayList<GetHeistMembers>();
        for (Member m : memberRepository.findAll()) {
            for (SkillMember sm : m.getSkills()) {
                for (SkillHeist sh : heistRepository.findHeistById(heist_id).getSkills()) {
                    if (sm.getName().equals(sh.getName())) {
                        Member member = memberRepository.findMemberByName(m.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member ne postoji"));
                        GetHeistMembers heistMembersByName = memberRepository.findHeistMembersById(member.getId());

                        getHeistMembers.add(new GetHeistMembers() {
                            @Override
                            public String getName() {
                                return heistMembersByName.getName();
                            }

                            @Override
                            public List<SkillMember> getSkills() {
                                return heistMembersByName.getSkills().stream().filter(p -> sh.getName().equals(p.getName())).collect(Collectors.toList());
                            }
                        });
                    }
                }
            }
        }
        return getHeistMembers;
    }

    public GetHeistEligibleMembers getHeistEligibleMembers(Long heist_id) {
        Heist heist = heistRepository.findById(heist_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Heist navedenog id-a ne postoji"));
        GetHeistEligibleMembers getHeistMembers = new GetHeistEligibleMembers() {
            @Override
            public List<SkillHeist> getSkills() {
                return heist.getSkills();
            }

            @Override
            public List<GetHeistMembers> getMembers() {
                List<GetHeistMembers> getHeistMembers = new ArrayList<GetHeistMembers>();
                int count1 = 0;
                int count2 = 0;
                for (Member m : memberRepository.findAll()) {
                    for (SkillMember sm : m.getSkills()) {
                        for (SkillHeist sh : heistRepository.findHeistById(heist_id).getSkills()) {
                            if (sm.getName().equals(sh.getName())) {
                                Member member = memberRepository.findMemberByName(m.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member ne postoji"));
                                GetHeistMembers heistMembersByName = memberRepository.findHeistMembersById(member.getId());
                                count2 = count2 + 1;
                                Optional<MemberHeist> memberHeistOptional = memberHeistRepository.findMemberHeist(heist_id, member.getId());
                                if (memberHeistOptional.isPresent()) {
                                    count1 = count1 + 1;
                                }
                                if ("AVAILABLE".equals(member.getStatus()) || "RETIRED".equals(member.getStatus())) {
                                } else {
                                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Members nema status AVAILABLE ili RETIRED");
                                }
                                getHeistMembers.add(new GetHeistMembers() {
                                    @Override
                                    public String getName() {
                                        return heistMembersByName.getName();
                                    }

                                    @Override
                                    public List<SkillMember> getSkills() {
                                        return heistMembersByName.getSkills().stream().filter(p -> sh.getName().equals(p.getName()) && p.getLevel().length() >= sh.getLevel().length()).collect(Collectors.toList());
                                    }
                                });
                            }
                        }
                    }
                }

                if (count1 == count2) {
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Svi members su potvrđeni u heistu");
                }

                return getHeistMembers;
            }
        };

        return getHeistMembers;
    }

    @Async("threadPoolTaskExecutor")
    public void setHeistStatusThread() throws InterruptedException {
        List<Heist> heistsList = getHeists();
        for (Heist heist : heistsList) {
            Date date1 = null;
            Date date2 = null;
            Date date = new Date();
            Date dateNow = null;
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(heist.getStartTime());
                date2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(heist.getEndTime());
                dateNow = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (dateNow.after(date1)) {
                logger.info("Heist by id " + heist.getId() + " - set status IN_PROGRESS");
                startHeist(heist.getId());
            }
            if (dateNow.after(date2)) {
                logger.info("Heist by id " + heist.getId() + " - set status FINISHED");
                finishHeist(heist.getId());
            }
        }
    }

    @Transactional
    public void finishHeist(Long heist_id) {
        Heist heist = heistRepository.findById(heist_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Heist navedenog id-a ne postoji"));
        if (!heist.getStatus().equals("IN_PROGRESS")) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Heist status nije IN_PROGRESS");
        }
        heist.setStatus("FINISHED");
    }

    @Async("threadPoolTaskExecutor")
    public void setMemberLevelThread() throws InterruptedException {
        for (Heist heist : getHeists()) {
            Date date1 = null;
            Date date2 = null;
            Date date = new Date();
            Date dateNow = null;
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(heist.getStartTime());
                date2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(heist.getEndTime());
                dateNow = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (dateNow.after(date1) && dateNow.before(date2)) {
                updateMemberLevel(heist.getId());
                System.out.println("Member level updated...");
            }
        }
    }

    public void updateMemberLevel(Long heist_id) {
        MemberService ms = new MemberService(memberRepository);
        List<SkillMember> skillMemberUpdate = new ArrayList<SkillMember>();
        SkillMember skillMember = new SkillMember();
        for (GetHeistMembers ghm : getHeistMembers(heist_id)) {
            Member member = memberRepository.findMemberByName(ghm.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member ne postoji"));
            List<SkillMember> skills = member.getSkills();
            for (SkillMember sm : skills) {
                if (sm.getLevel().length() < 10) {
                    skillMember.setName(sm.getName());
                    skillMember.setLevel(sm.getLevel() + "*");
                    skills.remove(sm);
                    skillMemberUpdate.add(skillMember);
                    member.setSkills(skillMemberUpdate);
                    ms.updateMemberSkills(member, member.getId());
                }
            }
        }
    }
}
