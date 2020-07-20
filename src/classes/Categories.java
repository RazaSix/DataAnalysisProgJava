package classes;

import java.util.ArrayList;
import java.util.Arrays;

public class Categories {

	
	private ArrayList<ArrayList<String>> categories = new ArrayList<ArrayList<String>>();
	private ArrayList<String> agriculture = new ArrayList<String>();
	private ArrayList<String> architecture = new ArrayList<String>();
	private ArrayList<String> biologicalSciences = new ArrayList<String>();
	private ArrayList<String> business = new ArrayList<String>();
	private ArrayList<String> communicationJournalism = new ArrayList<String>();
	private ArrayList<String> technology = new ArrayList<String>();
	private ArrayList<String> education = new ArrayList<String>();
	private ArrayList<String> engineering = new ArrayList<String>();
	private ArrayList<String> legal = new ArrayList<String>();
	private ArrayList<String> libArtsHumanities = new ArrayList<String>();
	private ArrayList<String> medicalHealth = new ArrayList<String>();
	private ArrayList<String> physics = new ArrayList<String>();
	private ArrayList<String> chemistry = new ArrayList<String>();
	private ArrayList<String> visualPerformingArts = new ArrayList<String>();
	private ArrayList<String> foodCulinaryArts = new ArrayList<String>();
	private ArrayList<String> mathematics = new ArrayList<String>();
	private ArrayList<String> socialMedia = new ArrayList<String>();
	private ArrayList<String> moviesnTV = new ArrayList<String>();
	private ArrayList<String> music = new ArrayList<String>();
	private ArrayList<String> booksFiction = new ArrayList<String>();
	private ArrayList<String> booksNonFiction = new ArrayList<String>();
	private ArrayList<String> history = new ArrayList<String>();
	private ArrayList<String> religion = new ArrayList<String>();
	private ArrayList<String> fashion = new ArrayList<String>();
	private ArrayList<String> general = new ArrayList<String>();
	
	
	private ArrayList<String> catNames = new ArrayList<String>();
	
	public Categories() {
		addCatTags();
		populateCategories();
		populateCatNames();
	}
	
	public void addCatTags() {
		catAgriculture();
		catArchitecture();
		catBiologicalSciences();
		catBusiness();
		catCommunicationJournalism();
		catTechnology();
		catEducation();
		catEngineering();
		catLegal();
		catLibArtsHumanities();
		catMedicalHealth();
		catPhysics();
		catChemistry();
		catVisualPerformingArts();
		catfoodCulinaryArts();
		catMathematics();
		catSocialMedia();
		catMoviesnTV();
		catMusic();
		catBooksFiction();
		catBooksNonFiction();
		catHistory();
		catReligion();
		catFashion();
		//catGeneral();
		
		
	}
	
	private void populateCategories() {
		categories.add(this.agriculture);
		categories.add(this.architecture);
		categories.add(this.biologicalSciences);
		categories.add(this.business);
		categories.add(this.communicationJournalism);
		categories.add(this.technology);
		categories.add(this.education);
		categories.add(this.engineering);
		categories.add(this.legal);
		categories.add(this.libArtsHumanities);
		categories.add(this.medicalHealth);
		categories.add(this.physics);
		categories.add(this.chemistry);
		categories.add(this.visualPerformingArts);
		categories.add(this.foodCulinaryArts);
		categories.add(this.mathematics);
		categories.add(this.socialMedia);
		categories.add(this.moviesnTV);
		categories.add(this.music);
		categories.add(this.booksFiction);
		categories.add(this.booksNonFiction);
		categories.add(this.history);
		categories.add(this.religion);
		categories.add(this.fashion);
		categories.add(this.general);
		
	}
	
	private void populateCatNames() {
		catNames.add("Agriculture");
		catNames.add("Architecture");
		catNames.add("Biological and Biomedical Sciences");
		catNames.add("Business");
		catNames.add("Communications and Journalism");
		catNames.add("Technology");
		catNames.add("Education");
		catNames.add("Engineering");
		catNames.add("Legal");
		catNames.add("Liberal Arts and Humanities");
		catNames.add("Medical and Health");
		catNames.add("Physics");
		catNames.add("Chemistry");
		catNames.add("Visual and Performing Arts");
		catNames.add("Food and Culinary Arts");
		catNames.add("Mathematics");
		catNames.add("Social Media");
		catNames.add("MoviesnTV");
		catNames.add("Music");
		catNames.add("Books Fiction");
		catNames.add("Books Non Fiction");
		catNames.add("History");
		catNames.add("Religion");
		catNames.add("Fashion");
		catNames.add("General");
	}
	
	private void catAgriculture() {
		this.agriculture = new ArrayList<>(Arrays.asList("Agriculture", "Farm", "Ranch", "Crop", "Horticulture", "Breeding", "Plant",
				"Season", "Food", "Greenhouse", "Nutrition", "Pests", "Pesticides", "Cattle", "Poultry", "Dairy", "Soil"));
	}
	
	private void catArchitecture() {
		this.architecture = new ArrayList<>(Arrays.asList("Planning", "Plans", "Designs", "Blueprints", "Interior"));
	}
	
	private void catBiologicalSciences() {
		this.biologicalSciences = new ArrayList<>(Arrays.asList("Biometrics", "Biostatistics", "Biotechnology", "Botany", "Genetics", "Biology",
				"Environmental", "Pharmacology","Toxicology", "Cardiovascular", "Neurobiology", "Neurophysiology", "Pathology",
				"Reproductive", "Reproduction", "Endocrinology", "Neuroscience", "Oncology", "Anatomy", "Cells", "Anatomy", "Bacteria", "Viruses",
				"Parasites", "Immune", "Zoology", "Ecology"));
	}
	
	private void catBusiness() {
		this.business = new ArrayList<>(Arrays.asList("Accounting", "Bookkeeping", "Money", "Accounts", "Finance", "Financial", "Tax", "Audit",
				"Economics", "Money", "Cashflow", "Banking", "Stocks", "Recession", "Credit", "Investment", "Markets", "Business", "Sales", "Management",
				"Entrepreneurship", "Merchandising", "Buying", "Resources", "Retail", "Commerce"));
	}
	
	private void catCommunicationJournalism() {
		this.communicationJournalism = new ArrayList<>(Arrays.asList("Communication", "Journalism", "TV", "Television", "News", "Newspaper", "Radio", "Reporters", "Journalists"));
	}
	
	private void catTechnology() {
		this.technology = new ArrayList<>(Arrays.asList("Artificial", "Intelligence", "AI", "IT", "Technology", "Programming"));
	}
	
	private void catEducation() {
		this.education = new ArrayList<>(Arrays.asList("Education", "University", "Teaching", "School", "Curriculum", "Tests", "Teachers", "College", "Modules", "Graduate", "Student"));
	}
	
	private void catEngineering() {
		this.engineering = new ArrayList<>(Arrays.asList("Engineering", "Engineer", "Aeronautical", "Astronautical", "Materials", "Civil", "Textile", "Systems", "Industrial", "Electrical",
				"Mechanical"));
	}
	
	private void catLegal() {
		this.legal = new ArrayList<>(Arrays.asList("Legal", "Lawyer", "Justice", "Criminal", "Probation", "Corrections", "Prison", "Jail", "Gaol", "Judge", "Court", "Parole", "Law",
				"Police"));
	}
	
	private void catLibArtsHumanities() {
		this.libArtsHumanities = new ArrayList<>(Arrays.asList("Humanities", "Culture", "Gender", "Ethnic", "Geography", "Cartography", "Huamnities", "Social", "Ethics", "Philosophy", "Religion",
				"Religious", "Political"));
	}
	
	private void catMedicalHealth() {
		this.medicalHealth = new ArrayList<>(Arrays.asList("Medicine", "Medical", "Doctor", "Dental", "Disorder", "Illness", "Disease", "Diagnostics", "Health", "Veterinary", "Veterinary",
				"Vet","Mental", "Nursing", "Nurse", "Pediatrician", "Surgeon", "Optometric", "Clinic", "Anesthesiology", "Anesthesia", "Cancer", "Neurologist", "GP", "Practitioner", 
				"Endocrinologist", "Neurologist", "Rheumatologist", "Allergy", "Rheumatologist", "Immunologist", "Psychiatrist",
				"Gynecologist", "Dermatologist", "Radiologist", "MRI", "Scan", "CT", "Cardiologist", "Dentist"));
	}
	
	private void catPhysics() {
		this.physics = new ArrayList<>(Arrays.asList("Physics", "Matter", "Nuclear", "Energy", "Electromagnetism", "Thermodynamics", "Quantum", "Mechanics", "Antimatter", "Gravity", "Relativity",
				"Mass", "Energy", "Spacetime","Theoritical", "Atomic"));
	}
	
	private void catChemistry() {
		this.chemistry = new ArrayList<>(Arrays.asList("Chemistry", "Organic", "Inorganic", "Chemist", "Moles", "Polymer","Elements", "Biochemistry", "Bonds", "Ions", "Salts", "Reaction", 
				"Covalent", "Oxidation", "Periodic", "Chemical"));
	}
	
	private void catVisualPerformingArts() {
		this.visualPerformingArts = new ArrayList<>(Arrays.asList("Dance", "Theatre", "Ballet", "Drama", "Art", "Perfomance", "Artists", "Play", "Photography", "Model"));
	}
	
	private void catfoodCulinaryArts() {
		this.foodCulinaryArts = new ArrayList<>(Arrays.asList("Cakes", "Baking", "Food", "Restaurant", "Catering", "Pastry", "Bartending", "Chef", "Meat", "Vegetables", "Dining", "Ingredients"));
	}
	
	private void catMathematics() {
		this.mathematics = new ArrayList<>(Arrays.asList("Maths", "Mathematics", "Euler", "Proofs", "Calculus", "Geometry", "Calculation", "Arithmetic", "Discrete", "Statistics", "Vectors",
				"Graph", "Matrices", "Linear", "Polynomials", "Angles", "Decimals", "Fractions", "Number"));
	}
	
	private void catSocialMedia() {
		this.socialMedia = new ArrayList<>(Arrays.asList("Facebook", "Twitter", "Instagram", "Social", "Media", "Hashtag", "Trending" ));
	}
	
	private void catMoviesnTV() {
		this.moviesnTV = new ArrayList<>(Arrays.asList("Film", "Movie", "Video", "Production", "Actor", "Poster", "Trailer", "Soundtrack", "Director", "Producer", "Thriller", "Script", "Horror", "Suspense",
				"Blu", "DVD", "Title", "Starring", "Cinema", "Animation", "Streaming", "Cinematography", "TV", "Season", "Series", "Episode", "Showrunner", "Guest"));
	}
	
	private void catMusic() {
		this.music = new ArrayList<>(Arrays.asList("Jazz", "Blues", "Rock", "Rap", "Music", "Songs", "Pop", "Singer", "Records", "Rockstar", "Lyrics"));
	}

	private void catBooksFiction() {
		this.booksFiction = new ArrayList<>(Arrays.asList("Fiction", "Fantasy", "Magic", "Elves", "Dwarves", "Wizard", "Witch", "Tolkien", "Dragon", "Author", "Plot"));
	}
	
	private void catBooksNonFiction() {
		this.booksNonFiction = new ArrayList<>(Arrays.asList("Biography", "Memoir", "Travel", "Guide", "Religion", "Productivity", "Essays", "Poetry", "Grammar", "Textbook"));
	}
	
	private void catHistory() {
		this.history = new ArrayList<>(Arrays.asList("History", "Archaeology", "Ancient", "Pharaoh", "Pyramids", "Romans", "Caesar", "WW1", "WW2", "Archduke", "Trenches", "Ages", "MedievaL", "Crusade", "Sphinx"));
	}
	
	private void catReligion() {
		this.religion = new ArrayList<>(Arrays.asList("God", "Jewish", "Christian", "Jesus", "Christianity", "Islam", "Mohammed", "Moses", "Bible", "Quaran", "Church", "Mosque", "Synagogue", "Hindi", "Reincarnation",
				"Heaven", "Hell", "Yahweh", "Allah", "Pray"));
	}
	
	private void catFashion() {
		this.fashion = new ArrayList<>(Arrays.asList("Catwalk", "Model", "Fashion", "Photoshoot", "Clothes", "Vogue"));
	}
	
	private void catGeneral() {
		//this.general = new ArrayList<>(Arrays.asList("xyz", "abc"));
	}

	
	
	//GETS
	public ArrayList<ArrayList<String>> getCategories() {
		return categories;
	}

	public ArrayList<String> getAgriculture() {
		return agriculture;
	}

	public ArrayList<String> getArchitecture() {
		return architecture;
	}

	public ArrayList<String> getBiologicalSciences() {
		return biologicalSciences;
	}

	public ArrayList<String> getBusiness() {
		return business;
	}

	public ArrayList<String> getCommunicationJournalism() {
		return communicationJournalism;
	}

	public ArrayList<String> getTechnology() {
		return technology;
	}

	public ArrayList<String> getEducation() {
		return education;
	}

	public ArrayList<String> getEngineering() {
		return engineering;
	}

	public ArrayList<String> getLegal() {
		return legal;
	}

	public ArrayList<String> getLibArtsHumanities() {
		return libArtsHumanities;
	}

	public ArrayList<String> getMedicalHealth() {
		return medicalHealth;
	}

	public ArrayList<String> getPhysics() {
		return physics;
	}

	public ArrayList<String> getChemistry() {
		return chemistry;
	}

	public ArrayList<String> getVisualPerformingArts() {
		return visualPerformingArts;
	}

	public ArrayList<String> getFoodCulinaryArts() {
		return foodCulinaryArts;
	}

	public ArrayList<String> getMathematics() {
		return mathematics;
	}

	public ArrayList<String> getSocialMedia() {
		return socialMedia;
	}

	public ArrayList<String> getMoviesnTV() {
		return moviesnTV;
	}

	public ArrayList<String> getMusic() {
		return music;
	}

	public ArrayList<String> getBooksFiction() {
		return booksFiction;
	}

	public ArrayList<String> getBooksNonFiction() {
		return booksNonFiction;
	}

	public ArrayList<String> getHistory() {
		return history;
	}

	public ArrayList<String> getReligion() {
		return religion;
	}

	public ArrayList<String> getFashion() {
		return fashion;
	}

	public ArrayList<String> getGeneral() {
		return general;
	}

	public ArrayList<String> getCatNames() {
		return catNames;
	}


	
	
}
